package id.ac.ui.cs.mobileprogramming.samuel.solasi.adapter;

import android.net.Uri;
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

import id.ac.ui.cs.mobileprogramming.samuel.solasi.R;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.UserProfileService;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusHolder> {

    private List<StatusModel> statusModels = new ArrayList<>();

    private UserProfileService userProfileService = new UserProfileService();

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
        StatusModel statusModel = statusModels.get(position);
        holder.textViewStatus.setText(statusModel.getDescription());
        userProfileService.getUserById(statusModel.getUuid()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    holder.textViewUsername.setText((String) document.get("displayName"));
                    holder.userPhoto.setImageURI(Uri.parse((String) document.get("photoUrl")));
                } else {
                    // Access local database
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
            textViewUsername = itemView.findViewById(R.id.text_username);
            textViewStatus = itemView.findViewById(R.id.status);
            userPhoto = itemView.findViewById(R.id.image_thumbnail);
        }
    }
}
