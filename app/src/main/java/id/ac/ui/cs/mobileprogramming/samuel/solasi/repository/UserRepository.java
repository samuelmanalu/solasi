package id.ac.ui.cs.mobileprogramming.samuel.solasi.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.dao.StatusDao;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.dao.UserDao;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.database.SolasiDatabase;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.AuthService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.UserProfileService;

public class UserRepository {

    private UserDao userDao;

    private AuthService authService;

    private UserProfileService userProfileService;

//    private LiveData<UserModel> userModelLiveData;

    private MutableLiveData<UserModel> userModelMutableLiveData;

    public UserRepository(Application application) {
        SolasiDatabase database = SolasiDatabase.getInstance(application);
        userDao = database.userDao();
        authService = new AuthService();
        userProfileService = new UserProfileService();
//        userModelLiveData = userDao.getUserLiveDataById(user.getUid());
        userModelMutableLiveData = new MutableLiveData<>();
    }

    public void signIn(String email, String password) {
        authService.signIn(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    try {
                        UserModel userModel = getUserInformation(authService.getUser().getUid());
                        userModelMutableLiveData.postValue(userModel);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void signUp() {

    }

    public UserModel getUserInformation(String uid) throws ExecutionException, InterruptedException {
        return new GetUserInfoAsyncTask(userDao).execute(uid).get();
    }

    private static class GetUserInfoAsyncTask extends AsyncTask<String, Void, UserModel> {

        private UserDao userDao;

        private GetUserInfoAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected UserModel doInBackground(String... strings) {
            return userDao.getUserById(strings[0]);
        }
    }

    public void syncUserData() {
        userProfileService.getUsers().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    UserModel userModel;
                    List<UserModel> userModels = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        userModel = userProfileService.generateUserModel(documentSnapshot);
                        userModels.add(userModel);
                    }
                    new UsersInfoSyncAsyncTask(userDao).execute(userModels);
                }
            }
        });
    }

    private static class UsersInfoSyncAsyncTask extends AsyncTask<List<UserModel>, Void, Void> {

        private UserDao userDao;

        public UsersInfoSyncAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(List<UserModel>... lists) {
            userDao.insertAll(lists[0]);
            return null;
        }
    }

    public MutableLiveData<UserModel> getUserModelMutableLiveData() {
        return userModelMutableLiveData;
    }

    //    public LiveData<UserModel> getUserModelLiveData() {
//        return userModelLiveData;
//    }
}
