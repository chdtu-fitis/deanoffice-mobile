package ua.edu.deanoffice.mobile.studentchdtu.applications.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ua.edu.deanoffice.mobile.studentchdtu.applications.model.Application;

public interface ApplicationRequests {

    @GET("applications")
    Call<Application> requestStudentInfo(@Query("applicationType") int id, @Query("json") String json);

    @GET("application-types")
    Call<ResponseBody> requestApplicationType();
}
