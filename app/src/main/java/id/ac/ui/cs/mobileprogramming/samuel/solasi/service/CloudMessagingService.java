package id.ac.ui.cs.mobileprogramming.samuel.solasi.service;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.R;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.pojo.Data;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.pojo.DataPayload;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.pojo.MasterPayload;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.pojo.Notification;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.util.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CloudMessagingService extends FirebaseMessagingService {

    private static final String TAG = "CloudMessagingService";

    public static final String FCM_URL = "https://fcm.googleapis.com";

    public static final String CONTENT_TYPE = "application/json";

    public static final String SERVER_KEY = "AAAA88dOc6Y:APA91bGWfnmZBhK_J0zjqVf8A40lPFTJCzNHn_lBOx7sXEdTsKIt9cnMGKv--zEPEP1CA7ngcAn14E0Vvp_EVt3m2BW-m27ccyvosXQYReZ00fswKKgM3JDSFCARxRNG__gjsoU4z2dm";

    private static final String DEFAULT_TOPIC = "solasi";

    public static Task<String> getCloudToken() {
        return FirebaseMessaging.getInstance().getToken();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.w(TAG, "onReceivedMessage Triggered");
        if (remoteMessage.getData().size() > 0 ) {
            Log.w(TAG, "Push Notification: " + remoteMessage.getData());
            createNotification(remoteMessage);
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.w(TAG, "New Token: " + s);
    }

    public static Task<Void> subscribeToTopics() {
        return FirebaseMessaging.getInstance().subscribeToTopic(DEFAULT_TOPIC);
    }

    public static void sendNotification(String title, String content) throws JsonProcessingException, JSONException {

        RetrofitClient retrofitClient = new RetrofitClient(FCM_URL);
        Notification notification = new Notification();
        notification.setBody(content);
        notification.setTitle(title);
        MasterPayload payload = new MasterPayload();
        payload.setNotification(notification);
        String topics = "/topics/" + DEFAULT_TOPIC;
        payload.setTo(topics);

        DataPayload dataPayload = new DataPayload();
        Data data = new Data();
        data.setTitle(title);
        data.setBody(content);
        dataPayload.setData(data);
        dataPayload.setTo(topics);

        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject jsonObject = new JSONObject(objectMapper.writeValueAsString(payload));

        Log.w(TAG, "Payload: " + jsonObject);

        retrofitClient.sendToEndPointBackground(payload).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.w(TAG, "Notification sent. Response: " + response.message());

                retrofitClient.sendToEndPointForeGround(dataPayload).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.w(TAG, "Data payload sent. Response: " + response.message());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.w(TAG, "Notification failed to sent. Response: " + t.getMessage());
            }
        });


    }

    public void createNotification(RemoteMessage remoteMessage) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("body"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

    }

}
