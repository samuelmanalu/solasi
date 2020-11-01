package id.ac.ui.cs.mobileprogramming.samuel.solasi.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;

public class StatusService {

    private static final String TAG = "AuthActivity";

    private FirebaseFirestore db;

    private String collection = "status";

    public StatusService() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void saveStatus(StatusModel statusModel) {
        statusModel.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> newStatus = objectMapper.convertValue(statusModel, Map.class);
        db.collection(collection)
                .add(newStatus)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.w(TAG, "Success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Failed");
                    }
                });
    }

    public static Date convertFirebaseTimestamp(long timestamp) {
        return new Date(timestamp);
    }
}
