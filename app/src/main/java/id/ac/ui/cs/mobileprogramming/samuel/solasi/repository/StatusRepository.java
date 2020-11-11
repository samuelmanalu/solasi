package id.ac.ui.cs.mobileprogramming.samuel.solasi.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.StatusService;

public class StatusRepository {

    private StatusService statusService;

    public StatusRepository() {
        this.statusService = new StatusService();
    }

    public void syncStatusDb() {
        statusService.getStatus().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<StatusModel> statusModels = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    StatusModel statusModel = convertToStatusModel(documentSnapshot);
                    statusModels.add(statusModel);
                }

                // Insert to local sql here
            }
        });
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
