package ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetApplication {
    @GET("generate-application")
    Call<ResponseBody> getRequest(@Query("applicationType") int id, @Query("json") String json);
}
