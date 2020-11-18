package id.ac.ui.cs.mobileprogramming.samuel.solasi.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserModel userModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserModel> userModels);

    @Update
    void update(UserModel userModel);

    @Delete
    void delete(UserModel userModel);

    @Delete
    void deleteAll(UserModel ... userModels);

    @Query("SELECT * FROM usermodel WHERE uid = :uid")
    UserModel getUserById(String uid);

    @Query("SELECT * FROM usermodel WHERE uid = :uid")
    LiveData<UserModel> getUserLiveDataById(String uid);
}
