package id.ac.ui.cs.mobileprogramming.samuel.solasi.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;

public class UserProfileService {

    private static final String TAG = "UserProfileService";

    private FirebaseFirestore db;

    private String collection = "user";

    private static final String DEFAULT_PHOTO_URL = "https://firebasestorage.googleapis.com/v0/b/tktpl-samuel.appspot.com/o/profil%2Fgrapefruit-slice-332-332.jpg?alt=media&token=9e562b71-6fe0-484d-9209-71fb736137e4";

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

    public Task<Void> saveUserInformation(UserModel userModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (userModel.getPhotoUrl() == null) {
            userModel.setPhotoUrl(DEFAULT_PHOTO_URL);
        }
        Map<String, Object> userData = objectMapper.convertValue(userModel, Map.class);
        userData.remove("uid");
        userData.remove("localPhotoUrl");
        return db.collection(collection).document(userModel.getUid()).set(userData);
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
