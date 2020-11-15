package id.ac.ui.cs.mobileprogramming.samuel.solasi.repository;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.dao.StatusDao;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.dao.UserDao;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.database.SolasiDatabase;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.UserModel;

public class UserRepository {

    private UserDao userDao;

    public UserRepository(Application application) {
        SolasiDatabase database = SolasiDatabase.getInstance(application);
        userDao = database.userDao();
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
}
