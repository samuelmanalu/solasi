package id.ac.ui.cs.mobileprogramming.samuel.solasi.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;

public class StatusService {

    private static final String TAG = "StatusService";

    private FirebaseFirestore db;

    private String collection = "status";

    public StatusService() {
        this.db = FirebaseFirestore.getInstance();
    }

    public Task<DocumentReference> saveStatus(String status, FirebaseUser user, String location) {
        StatusModel statusModel = setDefaultStatusModel(status, user);
        if (location != null) {
            statusModel.setLocation(location);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> newStatus = objectMapper.convertValue(statusModel, Map.class);
        newStatus.remove("id");
        return db.collection(collection)
                .add(newStatus);
    }

    public Task<Void> updateStatus(StatusModel statusModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> updatedStatus = objectMapper.convertValue(statusModel, Map.class);
        updatedStatus.remove("id");
        return db.collection(collection).document(statusModel.getId())
                .set(updatedStatus);
    }

    public static Date convertFirebaseTimestamp(long timestamp) {
        return new Date(timestamp);
    }

    public Task<QuerySnapshot> getStatus() {
        return  db.collection(collection).get();
    }

    public StatusModel setDefaultStatusModel(String status, FirebaseUser user) {
        StatusModel statusModel = new StatusModel();
        statusModel.setCreatedAt(new Date());
        statusModel.setIsEdited(false);
        statusModel.setImageUrl(null);
        statusModel.setIsImageExist(false);
        statusModel.setIsEdited(false);
        statusModel.setDescription(status);
        statusModel.setUuid(user.getUid());
        statusModel.setTotalLiked(0);
        statusModel.setRelatedStatus(null);
        return statusModel;
    }
}
