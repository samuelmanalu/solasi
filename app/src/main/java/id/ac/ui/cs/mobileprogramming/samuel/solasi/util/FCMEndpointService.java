package id.ac.ui.cs.mobileprogramming.samuel.solasi.util;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.pojo.MasterPayload;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FCMEndpointService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA88dOc6Y:APA91bGWfnmZBhK_J0zjqVf8A40lPFTJCzNHn_lBOx7sXEdTsKIt9cnMGKv--zEPEP1CA7ngcAn14E0Vvp_EVt3m2BW-m27ccyvosXQYReZ00fswKKgM3JDSFCARxRNG__gjsoU4z2dm" // Your server key refer to video for finding your server key
    })
    @POST("fcm/send")
    Call<ResponseBody> sendNotification(@Body MasterPayload params);
}
