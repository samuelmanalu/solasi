package id.ac.ui.cs.mobileprogramming.samuel.solasi.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.R;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.AuthService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.UserProfileService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.view.main.AddStatusFragment;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.view.main.ExtrasFragment;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.view.main.NotificationFragment;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.view.main.StatusFragment;

public class HomeScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeScreenActivity";

    private DrawerLayout drawer;

    private AuthService authService;

    private UserProfileService userProfileService;

    private ImageView profilePhoto;

    private TextView mUsername, mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        authService = new AuthService();
        userProfileService = new UserProfileService();

        if (!authService.isSignedIn()) {
            // When user not logged in
        }

        FirebaseUser user = authService.getUser();
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName("Bambang")
                .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/tktpl-samuel.appspot.com/o/profil%2Fgrapefruit-slice-332-332.jpg?alt=media&token=9e562b71-6fe0-484d-9209-71fb736137e4"))
                .build();
        user.updateProfile(profileChangeRequest);

        FloatingActionButton fab = findViewById(R.id.fab_test);
        Toolbar toolbar = findViewById(R.id.toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_test);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        mUsername = headerView.findViewById(R.id.username_nav);
        mEmail = headerView.findViewById(R.id.email_nav);
        profilePhoto = headerView.findViewById(R.id.imageView);

        mUsername.setText(user.getDisplayName());
        mEmail.setText(user.getEmail());
        if (user.getPhotoUrl() != null) {
            try {
                Log.w(TAG, user.getPhotoUrl().toString());
                profilePhoto.setImageBitmap(userProfileService.getImageBit(user.getPhotoUrl().toString()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ActionBarDrawerToggle toggle  = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new StatusFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddStatusFragment()).commit();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NotificationFragment()).commit();
                break;
            case R.id.nav_extras:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ExtrasFragment()).commit();
                break;
            case R.id.nav_logout:
                authService.signOut();
                Intent intent = new Intent(HomeScreenActivity.this, AuthActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new StatusFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}