package id.ac.ui.cs.mobileprogramming.samuel.solasi.viewmodel;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.repository.StatusRepository;

public class StatusViewModel extends AndroidViewModel {

    private StatusRepository statusRepository;

    private LiveData<List<StatusModel>> mStatusModel;

    public StatusViewModel(@NonNull Application application) {
        super(application);
        statusRepository = new StatusRepository(application);

        ConnectivityManager cm = (ConnectivityManager) application.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            // connected to the internet
            statusRepository.syncStatusFromFirebaseToDb();
        }
        this.mStatusModel = statusRepository.getAllStatus();
    }

    public void insertStatus(String status, FirebaseUser user) {
        statusRepository.insertStatus(status, user);
    }

    public LiveData<List<StatusModel>> getmStatusModel() {
        return mStatusModel;
    }
}
