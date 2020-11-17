package id.ac.ui.cs.mobileprogramming.samuel.solasi.adapter;

import android.app.Application;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.R;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.NotificationModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.repository.UserRepository;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.UserProfileService;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder>{

    private List<NotificationModel> notifications = new ArrayList<>();

    private UserProfileService userProfileService = new UserProfileService();

    private Application application;

    private UserRepository userRepository;

    public NotificationAdapter(Application application) {
        this.application = application;
        this.userRepository = new UserRepository(application);
    }

    public void setNotifications(List<NotificationModel> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new NotificationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationHolder holder, int position) {
        final NotificationModel notificationModel = notifications.get(position);

        /**
         * Action identifier
         * isLiked = true, user liked the status
         * isLiked = false, user reply the status
         */
//        String notificationText = Resources.getSystem().getString(R.string.notification_reply);
        String notificationText = application.getResources().getString(R.string.notification_reply);
        if (notificationModel.isLiked()) {
            notificationText = application.getResources().getString(R.string.notification_like);
//            notificationText = Resources.getSystem().getString(R.string.notification_like);
        }

        holder.textViewNotification.setText(notificationText);

        userProfileService.getUserById(notificationModel.getUidSender()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    holder.textViewUsernameSender.setText((String) document.get("displayName"));
                    holder.userNotificationPhoto.setImageURI(Uri.parse((String) document.get("photoUrl")));
                } else {
                    try {
                        UserModel userModel = userRepository.getUserInformation(notificationModel.getUidSender());
                        holder.textViewUsernameSender.setText(userModel.getDisplayName());
                        holder.userNotificationPhoto.setImageURI(Uri.parse(userModel.getPhotoUrl()));
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
        return notifications.size();
    }

    class NotificationHolder extends RecyclerView.ViewHolder {

        private TextView textViewUsernameSender, textViewNotification;
        private ImageView userNotificationPhoto;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            textViewNotification = itemView.findViewById(R.id.notification_text);
            textViewUsernameSender = itemView.findViewById(R.id.notification_text_username_sender);
            userNotificationPhoto = itemView.findViewById(R.id.notification_image_thumbnail);
        }
    }
}
