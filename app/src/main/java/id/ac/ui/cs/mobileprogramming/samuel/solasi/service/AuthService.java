package id.ac.ui.cs.mobileprogramming.samuel.solasi.service;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;

public class AuthService {

    private FirebaseAuth mAuth;

    public AuthService() {
        mAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> signIn(String email, String password){
        return mAuth.signInWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> signUp(String email, String password) {
        return mAuth.createUserWithEmailAndPassword(email, password);
    }

    public boolean isSignedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        return true;
    }

    public Task<Void> updateUserInformation(UserModel userModel) {
        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(userModel.getDisplayName())
                .setPhotoUri(Uri.parse(userModel.getPhotoUrl())).build();

        return mAuth.getCurrentUser().updateProfile(userProfileChangeRequest);
    }

    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

    public void signOut(){
        mAuth.signOut();
    }

}
