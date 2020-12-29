package id.ac.ui.cs.mobileprogramming.samuel.solasi.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.R;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.AuthService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.CloudMessagingService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.LocationService;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "AuthActivity";

    private EditText emailTextEdit, passwordTextEdit;

    private ProgressBar progressBar;

    private TextView isSignUpTv;

    private Button signUpBv;

//    private FirebaseAuth mAuth;

    private AuthService authService;

    private LocationService locationService;

    private FirebaseUser user;

    private boolean isSignUp = false;

    private String signUpPromt, singInPromt, signInButtonText, signUpButtonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            checkPermission();
        }

        CloudMessagingService.getCloudToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Failed to get Token");
                    return;
                }
                Log.w(TAG, "Token: " + task.getResult());
            }
        });

        CloudMessagingService.subscribeToTopics().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Failed to subscribe to topics");
                }
                Log.w(TAG, "Topics subscribed");
            }
        });

        // Declare placeholder
        emailTextEdit = findViewById(R.id.email);
        passwordTextEdit = findViewById(R.id.password);
        progressBar = findViewById(R.id.loading);
        signUpBv = findViewById(R.id.sign_up_bv);
        isSignUpTv = findViewById(R.id.textPromp);


        // Load String resources
        signUpPromt = getString(R.string.sign_up_prompt);
        singInPromt = getString(R.string.sign_in_prompt);
        signInButtonText = getString(R.string.action_sign_in_short);
        signUpButtonText = getString(R.string.action_sign_up_short);

        // Update state for sign in or sign up
        updateSignUpToggleState();

        // Initiate Firebase Authentication
//        mAuth = FirebaseAuth.getInstance();
        authService = new AuthService();
    }

    private void registerUser() {
        String emailValue = emailTextEdit.getText().toString().trim();
        String passwordValue = passwordTextEdit.getText().toString().trim();

        if (emailValue.isEmpty()) {
            emailTextEdit.setError(getString(R.string.email_required));
            emailTextEdit.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            emailTextEdit.setError(getString(R.string.invalid_email));
            emailTextEdit.requestFocus();
            return;
        }

        if (passwordValue.isEmpty()) {
            passwordTextEdit.setError(getString(R.string.password_required));
            passwordTextEdit.requestFocus();
            return;
        }

        if (passwordValue.length() < 8) {
            passwordTextEdit.setError(getString(R.string.invalid_password));
            passwordTextEdit.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // References
//        Task<AuthResult> newTask = mAuth.createUserWithEmailAndPassword(emailValue, passwordValue);

        authService.signUp(emailValue, passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.w(TAG, "Sign up complete");
                    Toast.makeText(getApplicationContext(), getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
                    isSignUp = !isSignUp;
                    updateSignUpToggleState();
                    resetField();
                    Intent intent = new Intent(AuthActivity.this, UpdateUserInfoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), getString(R.string.email_already_registered), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void toggleSignUp(View view) {
        isSignUp = !isSignUp;
        updateSignUpToggleState();
    }

    private void updateSignUpToggleState() {
        if (isSignUp) {
            isSignUpTv.setText(singInPromt);
            signUpBv.setText(signUpButtonText);
        } else {
            isSignUpTv.setText(signUpPromt);
            signUpBv.setText(signInButtonText);
        }
    }

    private void signInUser() {
        String emailValue = emailTextEdit.getText().toString().trim();
        String passwordValue = passwordTextEdit.getText().toString().trim();

        if (emailValue.isEmpty()) {
            emailTextEdit.setError(getString(R.string.email_required));
            emailTextEdit.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            emailTextEdit.setError(getString(R.string.invalid_email));
            emailTextEdit.requestFocus();
            return;
        }

        if (passwordValue.isEmpty()) {
            passwordTextEdit.setError(getString(R.string.password_required));
            passwordTextEdit.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        authService.signIn(emailValue, passwordValue)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            user = authService.getUser();
                            Log.w(TAG, user.getEmail() + " logged in");
                            String welcomeText = getString(R.string.welcome, user.getEmail());
                            Toast.makeText(getApplicationContext(), welcomeText, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AuthActivity.this, HomeScreenActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public void onSignUpButtonClicked(View view) {
        if (isSignUp) {
            registerUser();
            return;
        } else {
            signInUser();
            return;
        }
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
            return;
        }

        locationService = new LocationService(this);
        locationService.getCurrentLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.w(TAG, "Location updated");
                if (location != null) {
                    Log.w(TAG, "Location: " + location.toString());
                } else {
                    locationService.requestLocation();
                    Log.w(TAG, "Location is null");
                }
            }
        });
    }

    private void resetField() {
        emailTextEdit.setText("");
        passwordTextEdit.setText("");
    }
}