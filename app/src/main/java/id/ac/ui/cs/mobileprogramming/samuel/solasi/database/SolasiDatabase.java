package id.ac.ui.cs.mobileprogramming.samuel.solasi.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.dao.NotificationDao;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.dao.StatusDao;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.dao.UserDao;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.NotificationModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;

@Database(entities = {StatusModel.class, UserModel.class, NotificationModel.class}, version = 1)
public abstract class SolasiDatabase extends RoomDatabase {

    private static SolasiDatabase instance;

    public abstract StatusDao statusDao();

    public abstract UserDao userDao();

    public abstract NotificationDao notificationDao();

    public static synchronized SolasiDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SolasiDatabase.class, "solasi")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
