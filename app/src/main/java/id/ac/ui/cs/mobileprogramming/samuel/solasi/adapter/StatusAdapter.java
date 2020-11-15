package id.ac.ui.cs.mobileprogramming.samuel.solasi.adapter;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.auth.User;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.R;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.repository.UserRepository;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.UserProfileService;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusHolder> {

    private static final String TAG = "StatusAdapter";

    private List<StatusModel> statusModels = new ArrayList<>();

    private UserProfileService userProfileService = new UserProfileService();

    private Application application;

    public StatusAdapter(Application application) {
        this.application = application;
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
        Log.w(TAG, "Test Adapter before fetch user data");
        userProfileService.getUserById(statusModel.getUuid()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.w(TAG, "Test Adapter");
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Log.w(TAG, "Username: " + document.get("displayName"));
                    Log.w(TAG, "User photo url: " + document.get("photoUrl"));
                    holder.textViewStatus.setText(statusModel.getDescription());
                    holder.textViewUsername.setText((String) document.get("displayName"));
                    holder.userPhoto.setImageURI(Uri.parse((String) document.get("photoUrl")));
                } else {
                    UserRepository userRepository = new UserRepository(application);
                    try {
                        UserModel userModel = userRepository.getUserInformation(statusModel.getUuid());
                        holder.textViewUsername.setText(userModel.getDisplayName());
//                        holder.userPhoto.setImageURI(Uri.parse(userModel.getPhotoUrl()));
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

        private TextView textViewUsername, textViewStatus;
        private ImageView userPhoto;

        public StatusHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewUsername = itemView.findViewById(R.id.text_username);
            this.textViewStatus = itemView.findViewById(R.id.text_status);
            this.userPhoto = itemView.findViewById(R.id.image_thumbnail);
        }
    }
}
