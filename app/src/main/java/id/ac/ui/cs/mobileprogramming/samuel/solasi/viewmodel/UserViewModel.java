package id.ac.ui.cs.mobileprogramming.samuel.solasi.viewmodel;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.repository.UserRepository;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.AuthService;

public class UserViewModel extends AndroidViewModel {

    UserRepository userRepository;

    AuthService authService;

    LiveData<UserModel> userModelLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
        authService = new AuthService();

        userRepository = new UserRepository(application);

        ConnectivityManager cm = (ConnectivityManager) application.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            userRepository.syncUserData();
        }
//        this.userModelLiveData = userRepository.getUserModelLiveData();
    }

    public LiveData<UserModel> getUserModelLiveData() {
        return userModelLiveData;
    }
}
