package id.ac.ui.cs.mobileprogramming.samuel.solasi.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.dao.StatusDao;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.database.SolasiDatabase;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.StatusService;

public class StatusRepository {

    private StatusDao statusDao;

    private StatusService statusService;

    private LiveData<StatusModel> allStatus;

    public StatusRepository(Application application) {
        SolasiDatabase database = SolasiDatabase.getInstance(application);
        statusDao = database.statusDao();
        statusService = new StatusService();
        allStatus = statusDao.getAllStatus();
    }

    public void syncStatusFromFirebaseToDb() {
        statusService.getStatus().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<StatusModel> statusModels = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    StatusModel statusModel = convertToStatusModel(documentSnapshot);
                    statusModels.add(statusModel);
                }
                // Insert to local sql here
                new InsertAllStatusAsyncTask(statusDao).execute(statusModels);
            }
        });
    }

    // Insert Status will be directly uploaded to Firebase
    public void insertStatus(String status, FirebaseUser user) {
        statusService.saveStatus(status, user);
    }

    public LiveData<StatusModel> getAllStatus() {
        return allStatus;
    }

    private static class InsertAllStatusAsyncTask extends AsyncTask<List<StatusModel>, Void, Void> {

        private StatusDao statusDao;

        private InsertAllStatusAsyncTask(StatusDao statusDao) {
            this.statusDao = statusDao;
        }

        @Override
        protected Void doInBackground(List<StatusModel>... lists) {
            statusDao.insertAll(lists[0]);
            return null;
        }
    }

    public StatusModel convertToStatusModel(QueryDocumentSnapshot document) {
        Map<String, Object> objectMap = document.getData();
        StatusModel statusModel = new StatusModel();
        statusModel.setId(document.getId());
        statusModel.setCreatedAt((Timestamp) objectMap.get("createdAt"));
        statusModel.setIsEdited((Boolean) objectMap.get("isEdited"));
        statusModel.setImageUrl((String) objectMap.get("imageUrl"));
        statusModel.setIsImageExist((Boolean) objectMap.get("isImageExist"));
        statusModel.setIsEdited((Boolean) objectMap.get("isEdited"));
        statusModel.setDescription((String) objectMap.get("description"));
        statusModel.setUuid((String) objectMap.get("uuid"));
        statusModel.setTotalLiked((Integer) objectMap.get("totalLiked"));
        statusModel.setRelatedStatus((String) objectMap.get("relatedStatus"));
        return statusModel;
    }
}
