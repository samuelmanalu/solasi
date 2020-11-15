package id.ac.ui.cs.mobileprogramming.samuel.solasi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.NotificationModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.repository.NotificationRepository;

public class NotificationViewModel extends AndroidViewModel {

    private NotificationRepository notificationRepository;

    private LiveData<List<NotificationModel>> notifications;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<NotificationModel>> getNotifications() {
        return notifications;
    }
}
