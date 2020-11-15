package id.ac.ui.cs.mobileprogramming.samuel.solasi.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.NotificationModel;

@Dao
public interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NotificationModel notificationModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NotificationModel> notificationModels);

    @Query("SELECT * FROm notificationmodel WHERE uidReceiver = :uidReceiver")
    LiveData<List<NotificationModel>> getNotification(String uidReceiver);
}
