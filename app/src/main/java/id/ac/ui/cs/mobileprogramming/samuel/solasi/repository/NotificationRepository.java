package id.ac.ui.cs.mobileprogramming.samuel.solasi.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.dao.NotificationDao;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.database.SolasiDatabase;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.NotificationModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.NotificationService;

public class NotificationRepository {

    private NotificationDao notificationDao;

    private NotificationService notificationService;

    private LiveData<List<NotificationModel>> notifications;

    private String uid;

    public NotificationRepository(Application application, String uid) {
        SolasiDatabase database = SolasiDatabase.getInstance(application);
        notificationDao = database.notificationDao();
        notificationService = new NotificationService();
        notifications = notificationDao.getNotification(uid);
        this.uid = uid;
    }

    public LiveData<List<NotificationModel>> getNotifications() {
        return notifications;
    }

    public Task<QuerySnapshot> syncNotificationFirebaseToDb() {
        return notificationService.getNotification(uid).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<NotificationModel> notificationModels = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    NotificationModel notificationModel = null;
                    try {
                        notificationModel = convertToNotificationModel(documentSnapshot);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    notificationModels.add(notificationModel);
                }
                // Insert to local sql here
                insertAllNotification(notificationModels);
            }
        });
    }

    public void insertAllNotification(List<NotificationModel> notificationModels) {
        new InsertAllNotificationAsyncTask(notificationDao).execute(notificationModels);
    }

    public static class InsertAllNotificationAsyncTask extends AsyncTask<List<NotificationModel>, Void, Void> {

        private NotificationDao notificationDao;

        private InsertAllNotificationAsyncTask(NotificationDao notificationDao) {
            this.notificationDao = notificationDao;
        }

        @Override
        protected Void doInBackground(List<NotificationModel>... lists) {
            notificationDao.insertAll(lists[0]);
            return null;
        }
    }

    public NotificationModel convertToNotificationModel(QueryDocumentSnapshot documentSnapshot) throws ParseException {
        NotificationModel notificationModel = new NotificationModel();
        Map<String, Object> objectMap = documentSnapshot.getData();
        notificationModel.setLiked((Boolean) objectMap.get("liked"));
        notificationModel.setRelatedStatusId((String) objectMap.get("relatedStatusId"));
        notificationModel.setUidReceiver((String) objectMap.get("uidReceiver"));
        notificationModel.setUidSender((String) objectMap.get("uidSender"));
        notificationModel.setId(documentSnapshot.getId());

        Integer value = ((Long) objectMap.get("createdAt")).intValue();
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = originalFormat.parse(value.toString());

        notificationModel.setCreatedAt(date);

        return notificationModel;
    }
}
