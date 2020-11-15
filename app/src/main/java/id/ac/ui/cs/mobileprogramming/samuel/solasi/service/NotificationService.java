package id.ac.ui.cs.mobileprogramming.samuel.solasi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.Map;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.NotificationModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;

public class NotificationService {

    private static final String TAG = "NotificationService";

    private FirebaseFirestore db;

    private String collection = "notification";

    public NotificationService(){
        db = FirebaseFirestore.getInstance();
    }

    public Task<DocumentReference> saveNotification(StatusModel statusModel, FirebaseUser user, Boolean isLiked) {
        ObjectMapper objectMapper = new ObjectMapper();
        NotificationModel notificationModel = setDefaultNotificationModel(statusModel, user, isLiked);
        Map<String, Object> newNotification = objectMapper.convertValue(notificationModel, Map.class);
        newNotification.remove("id");
        return db.collection(collection)
                .add(newNotification);
    }

    public NotificationModel setDefaultNotificationModel(StatusModel statusModel, FirebaseUser user, Boolean isLiked) {
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        notificationModel.setUidSender(user.getUid());
        notificationModel.setUidReceiver(statusModel.getUuid());
        notificationModel.setRelatedStatusId(statusModel.getId());
        notificationModel.setLiked(isLiked);
        return notificationModel;
    }

    public Task<QuerySnapshot> getNotification(String uid) {
        return  db.collection(collection).whereEqualTo("uidReceiver", uid).get();
    }

}
