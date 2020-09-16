package ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetApplicationTypeList {

    @GET("application-type")
    public Call<ResponseBody> getApplicationTypeList();
}
