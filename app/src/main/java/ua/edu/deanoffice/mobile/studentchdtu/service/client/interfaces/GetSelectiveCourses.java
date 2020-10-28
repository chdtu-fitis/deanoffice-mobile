package ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetSelectiveCourses {

    @Headers({"Content-Type: application/json"})
    @GET("selective-courses")
    Call<ResponseBody> request(@Header("Authorization") String token, @Query("semester") int semester);
}
