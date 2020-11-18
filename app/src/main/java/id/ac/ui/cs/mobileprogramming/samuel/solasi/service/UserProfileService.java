package id.ac.ui.cs.mobileprogramming.samuel.solasi.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.dao.UserDao;
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

    public Task<QuerySnapshot> getUsers() {
        return  db.collection(collection).get();
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

    public UserModel generateUserModel(QueryDocumentSnapshot documentSnapshot) {
        UserModel userModel = new UserModel();
        userModel.setDisplayName((String) documentSnapshot.get("displayName"));
        userModel.setPhotoUrl((String) documentSnapshot.get("photoUrl"));
        userModel.setUid((String) documentSnapshot.get("uid"));
        return userModel;
    }

    public Bitmap getImageBit(String urlString) throws IOException, ExecutionException, InterruptedException {
        URL url = new URL(urlString);
        return new GetUserPhotoAsyncTask().execute(url).get();
    }

    private static class GetUserPhotoAsyncTask extends AsyncTask<URL, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(URL... urls) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(urls[0].openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace()
                ;
            }
            return bitmap;
        }
    }
}
