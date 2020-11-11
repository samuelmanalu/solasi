package id.ac.ui.cs.mobileprogramming.samuel.solasi.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.NotificationModel;

@Dao
public interface NotificationDao {

    @Insert
    void insert(NotificationModel notificationModel);

    @Query("SELECT * FROm notificationmodel WHERE uidReceiver = :uidReceiver")
    List<NotificationModel> getNotification(String uidReceiver);
}
