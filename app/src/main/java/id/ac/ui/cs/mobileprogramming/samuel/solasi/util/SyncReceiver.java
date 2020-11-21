package id.ac.ui.cs.mobileprogramming.samuel.solasi.util;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.repository.NotificationRepository;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.repository.StatusRepository;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.repository.UserRepository;

public class SyncReceiver extends BroadcastReceiver {

    private StatusRepository statusRepository;

    private UserRepository userRepository;

    @Override
    public void onReceive(Context context, Intent intent) {

        userRepository = new UserRepository((Application) context.getApplicationContext());
        statusRepository = new StatusRepository((Application) context.getApplicationContext());

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null) {
            userRepository.syncUserData();
            statusRepository.syncStatusFromFirebaseToDb();
        }
    }
}
