package id.ac.ui.cs.mobileprogramming.samuel.solasi;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.AuthService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

//    private FirebaseAuth mAuth;

    private AuthService authService;

    private TextView txViewFirstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authService = new AuthService();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txViewFirstFragment = findViewById(R.id.textview_first);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Initialize Firebase Auth

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = authService.getUser();
        if (currentUser != null) {
            Log.w(TAG, "current user: " + currentUser.getEmail());
//            txViewFirstFragment.setText("Hello " + currentUser.getEmail());
        }
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}