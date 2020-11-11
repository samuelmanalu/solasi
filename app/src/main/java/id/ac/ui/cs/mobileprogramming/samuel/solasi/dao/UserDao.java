package id.ac.ui.cs.mobileprogramming.samuel.solasi.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;

@Dao
public interface UserDao {

    @Insert
    void insert(UserModel userModel);

    @Update
    void update(UserModel userModel);

    @Delete
    void delete(UserModel userModel);

    @Delete
    void deleteAll(UserModel ... userModels);

    @Query("SELECT * FROM usermodel WHERE uid = :uid")
    UserModel getUserById(String uid);
}
