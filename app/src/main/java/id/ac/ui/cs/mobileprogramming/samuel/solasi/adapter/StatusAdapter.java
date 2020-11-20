package id.ac.ui.cs.mobileprogramming.samuel.solasi.adapter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.R;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.repository.UserRepository;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.AuthService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.NotificationService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.StatusService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.UserProfileService;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusHolder> {

    private static final String TAG = "StatusAdapter";

    private List<StatusModel> statusModels = new ArrayList<>();

    private UserProfileService userProfileService = new UserProfileService();

    private StatusService statusService = new StatusService();

    private NotificationService notificationService = new NotificationService();

    private Application application;

    private UserRepository userRepository;

    private AuthService authService = new AuthService();

    public StatusAdapter(Application application) {
        this.application = application;
        this.userRepository = new UserRepository(application);
    }

    public void setStatusModels(List<StatusModel> statusModels) {
        this.statusModels = statusModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.status_item, parent, false);
        return new StatusHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final StatusHolder holder, int position) {
        final StatusModel statusModel = statusModels.get(position);
//        holder.textViewUsername.setText("Test Username");
        holder.textViewStatus.setText(statusModel.getDescription());
        @SuppressLint("StringFormatMatches") String likeText = application.getResources().getString(R.string.like_placeholder, statusModel.getTotalLiked());
        holder.textLikePlaceholder.setText(likeText);
        holder.setStatusModel(statusModel);
        userProfileService.getUserById(statusModel.getUuid()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    holder.textViewUsername.setText((String) document.get("displayName"));
                    try {
                        holder.userPhoto.setImageBitmap(userProfileService.getImageBit((String) document.get("photoUrl")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        UserModel userModel = userRepository.getUserInformation(statusModel.getUuid());
                        holder.textViewUsername.setText(userModel.getDisplayName());
                        try {
                            holder.userPhoto.setImageBitmap(userProfileService.getImageBit(userModel.getPhotoUrl()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return statusModels.size();
    }

    class StatusHolder extends RecyclerView.ViewHolder {

        private TextView textViewUsername, textViewStatus, textLikePlaceholder;
        private ImageView userPhoto;
        private LinearLayout likeLayout;
        private StatusModel statusModel;

        public StatusHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewUsername = itemView.findViewById(R.id.text_username);
            this.textViewStatus = itemView.findViewById(R.id.text_status);
            this.userPhoto = itemView.findViewById(R.id.image_thumbnail);
            this.likeLayout = itemView.findViewById(R.id.layout_like);
            this.textLikePlaceholder = itemView.findViewById(R.id.like_placeholder);
            this.likeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    statusModel.setTotalLiked(statusModel.getTotalLiked() + 1);
                    notificationService.getNotificationWithActionAndStatusIdAndUserId(statusModel.getId(), authService.getUser().getUid(), true).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().isEmpty()) {
                                    statusService.updateStatus(statusModel)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        notificationService.saveNotification(statusModel, authService.getUser(), true);
                                                        notifyDataSetChanged();
                                                    }
                                                }
                                            });
                                } else {
                                    // User Already Liked.
                                    Toast.makeText(application.getApplicationContext(), application.getResources().getString(R.string.already_like_warning), Toast.LENGTH_SHORT).show();
                                }
                            } else {

                            }
                        }
                    });
                }
            });
        }

        public void setStatusModel(StatusModel statusModel) {
            this.statusModel = statusModel;
        }
    }
}
