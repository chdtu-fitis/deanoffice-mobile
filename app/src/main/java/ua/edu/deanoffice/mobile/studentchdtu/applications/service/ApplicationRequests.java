package ua.edu.deanoffice.mobile.studentchdtu.applications.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import ua.edu.deanoffice.mobile.studentchdtu.BuildConfig;
import ua.edu.deanoffice.mobile.studentchdtu.applications.model.Application;

public interface ApplicationRequests {

    @GET("applications")
    @Headers("X-App-Version: " + BuildConfig.VERSION_NAME)
    Call<Application> requestStudentInfo(@Query("json") String json, @Query("applicationType") int id, @Header("Authorization") String token);
}
