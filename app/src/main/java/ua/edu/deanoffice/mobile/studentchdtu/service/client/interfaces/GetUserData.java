package ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface GetUserData {

    @Headers({"Content-Type: application/json"})
    @GET("info-about-student")
    public Call<ResponseBody> getApplicationTypeList(@Header("Authorization") String token);
}
