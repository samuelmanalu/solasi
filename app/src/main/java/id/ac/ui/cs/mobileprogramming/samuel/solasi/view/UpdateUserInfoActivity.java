package id.ac.ui.cs.mobileprogramming.samuel.solasi.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.R;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.AuthService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.StorageService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.UserProfileService;

public class UpdateUserInfoActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;

    private EditText mDisplayName;

    private AuthService authService;

    private UserProfileService userProfileService;

    private ImageView mImagePlaceholder;

    private ProgressBar progressBar;

    private Button mSaveButton;

    private Uri profilePicture;

    private StorageService storageService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        authService = new AuthService();
        userProfileService = new UserProfileService();
        storageService = new StorageService();

        if (!authService.isSignedIn()) {
            Intent intent = new Intent(UpdateUserInfoActivity.this, AuthActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }

        mDisplayName = findViewById(R.id.display_name_input);
        mImagePlaceholder = findViewById(R.id.img_set);
        mSaveButton = findViewById(R.id.btn_save);
        progressBar = findViewById(R.id.loading_sign_up);

        mImagePlaceholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser(view);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String displayName = mDisplayName.getText().toString().trim();
                final UserModel userModel = new UserModel();
                userModel.setUid(authService.getUser().getUid());
                userModel.setDisplayName(displayName);

                if (profilePicture != null) {
                    final String fileName = authService.getUser().getUid() + ".jpg";
                    storageService.storeStatusImageFile(profilePicture, fileName).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            storageService.getDownloadedUrl(fileName).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    userModel.setPhotoUrl(task.getResult().toString());
                                    userProfileService.saveUserInformation(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            authService.updateUserInformation(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    progressBar.setVisibility(View.GONE);
                                                    goToHomeScreen();
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else {
                    userModel.setPhotoUrl(null);
                    userProfileService.saveUserInformation(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            goToHomeScreen();
                        }
                    });
                }

            }
        });
    }

    public void goToHomeScreen() {
        Intent intent = new Intent(UpdateUserInfoActivity.this, HomeScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            profilePicture = data.getData();

            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePicture);
                mImagePlaceholder.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageChooser(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_profile_image)), CHOOSE_IMAGE);
    }
}
