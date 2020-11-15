package id.ac.ui.cs.mobileprogramming.samuel.solasi.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.R;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.adapter.StatusAdapter;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.UserProfileService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.viewmodel.StatusViewModel;

public class HomeActivity extends AppCompatActivity {

    private StatusViewModel statusViewModel;

//    private UserProfileService userProfileService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        userProfileService = new UserProfileService();
        RecyclerView recyclerView = findViewById(R.id.status_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final StatusAdapter adapter = new StatusAdapter(getApplication());
        recyclerView.setAdapter(adapter);

        statusViewModel = ViewModelProviders.of(this).get(StatusViewModel.class);
        statusViewModel.getmStatusModel().observe(this, new Observer<List<StatusModel>>() {
            @Override
            public void onChanged(List<StatusModel> statusModels) {
                adapter.setStatusModels(statusModels);
            }
        });
    }
}