package id.ac.ui.cs.mobileprogramming.samuel.solasi.service;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

    public void signOut(){
        mAuth.signOut();
    }

}
