package id.ac.ui.cs.mobileprogramming.samuel.solasi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.repository.StatusRepository;

public class StatusViewModel extends AndroidViewModel {

    private StatusRepository statusRepository;

    private LiveData<StatusModel> mStatusModel;

    public StatusViewModel(@NonNull Application application) {
        super(application);
        statusRepository = new StatusRepository(application);
        this.mStatusModel = statusRepository.getAllStatus();
    }

    public void insertStatus(String status, FirebaseUser user) {
        statusRepository.insertStatus(status, user);
    }

    public LiveData<StatusModel> getStatus() {
        return mStatusModel;
    }
}
