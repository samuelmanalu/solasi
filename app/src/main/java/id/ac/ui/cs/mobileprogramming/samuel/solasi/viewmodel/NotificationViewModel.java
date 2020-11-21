package id.ac.ui.cs.mobileprogramming.samuel.solasi.viewmodel;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.NotificationModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.repository.NotificationRepository;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.AuthService;

public class NotificationViewModel extends AndroidViewModel {

    private NotificationRepository notificationRepository;

    private LiveData<List<NotificationModel>> notifications;

    private AuthService authService;

    public NotificationViewModel(@NonNull Application application) {
        super(application);

        authService = new AuthService();

        notificationRepository = new NotificationRepository(application, authService.getUser().getUid());

        ConnectivityManager cm = (ConnectivityManager) application.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        this.notifications = notificationRepository.getNotifications();
        if (activeNetwork != null) {
            notificationRepository.syncNotificationFirebaseToDb();
        }
    }

    public LiveData<List<NotificationModel>> getNotifications() {
        return notifications;
    }
}
