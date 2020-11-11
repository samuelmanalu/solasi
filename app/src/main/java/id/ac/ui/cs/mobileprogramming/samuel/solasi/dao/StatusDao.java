package id.ac.ui.cs.mobileprogramming.samuel.solasi.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;

@Dao
public interface StatusDao {

    @Insert
    void insert(StatusModel statusModel);

    @Update
    void update(StatusModel statusModel);

    @Delete
    void delete(StatusModel statusModel);

    @Query("SELECT * FROM statusmodel")
    List<StatusModel> getAllStatus();
}
