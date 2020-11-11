package id.ac.ui.cs.mobileprogramming.samuel.solasi.viewmodel;

import android.net.ConnectivityManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;

public class StatusViewModel extends ViewModel {

    private MutableLiveData<List<StatusModel>> mStatusModel;

    public LiveData<List<StatusModel>> getStatus() {
        return mStatusModel;
    }
}
