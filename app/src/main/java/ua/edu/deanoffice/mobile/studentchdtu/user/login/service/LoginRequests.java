package ua.edu.deanoffice.mobile.studentchdtu.user.login.service;

import androidx.annotation.Keep;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import ua.edu.deanoffice.mobile.studentchdtu.BuildConfig;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.model.Credentials;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.model.JWToken;

@Keep
public interface LoginRequests {

    @POST("api/authenticate")
    @Headers("X-App-Version: "+ BuildConfig.VERSION_NAME)
    Call<JWToken> requestAuthStudent(@Body Credentials cred);
}
