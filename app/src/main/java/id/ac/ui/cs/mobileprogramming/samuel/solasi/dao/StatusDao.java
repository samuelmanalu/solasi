package id.ac.ui.cs.mobileprogramming.samuel.solasi.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;

@Dao
public interface StatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StatusModel statusModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<StatusModel> statusModels);

    @Update
    void update(StatusModel statusModel);

    @Delete
    void delete(StatusModel statusModel);

    @Query("SELECT * FROM statusmodel")
    LiveData<List<StatusModel>> getAllStatus();
}
