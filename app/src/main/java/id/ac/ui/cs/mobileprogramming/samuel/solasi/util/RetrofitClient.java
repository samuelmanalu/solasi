package id.ac.ui.cs.mobileprogramming.samuel.solasi.util;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.pojo.DataPayload;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.pojo.MasterPayload;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String TAG = "WifiListService";

    private Retrofit retrofit;

    private FCMEndpointService fcmEndpointService;

    public RetrofitClient(String endpoint) {
        retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fcmEndpointService = retrofit.create(FCMEndpointService.class);
    }

    public Call<ResponseBody> sendToEndPointBackground(MasterPayload baseRequestJson) {
        return fcmEndpointService.sendNotification(baseRequestJson);
    }

    public Call<ResponseBody> sendToEndPointForeGround(DataPayload baseRequestJson) {
        return fcmEndpointService.sendData(baseRequestJson);
    }

}
