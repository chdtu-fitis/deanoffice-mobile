package ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.UserData.Credentials;

public interface PostRequest {
    @Headers({"Content-Type: application/json", "Accept: */*",
    "Accept-Encoding: gzip, deflate, br"})
    @POST("api/authenticate")
    Call<ResponseBody> request(@Body Credentials cred);
}