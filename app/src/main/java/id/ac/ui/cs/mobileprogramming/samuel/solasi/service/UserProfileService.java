package id.ac.ui.cs.mobileprogramming.samuel.solasi.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;

public class UserProfileService {

    private static final String TAG = "UserProfileService";

    private FirebaseFirestore db;

    private String collection = "user";

    public UserProfileService() {
        db = FirebaseFirestore.getInstance();
    }


    public Task<DocumentSnapshot> getUserById(String id) {
        UserModel userModel = new UserModel();
        return db.collection(collection).document(id).get();
    }

    public void saveUserInformation(UserModel userModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> userData = objectMapper.convertValue(userModel, Map.class);
        userData.remove("uid");
        userData.remove("localPhotoUrl");
        db.collection(collection)
                .add(userData)
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
}
