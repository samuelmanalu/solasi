package id.ac.ui.cs.mobileprogramming.samuel.solasi.service;

import com.google.firebase.firestore.FirebaseFirestore;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;

public class UserProfileService {

    private static final String TAG = "UserProfileService";

    private FirebaseFirestore db;

    private String collection = "status";

    public UserProfileService() {
        db = FirebaseFirestore.getInstance();
    }


    public UserModel getUserById(String id) {
        UserModel userModel = new UserModel();
        return userModel;
    }
}
