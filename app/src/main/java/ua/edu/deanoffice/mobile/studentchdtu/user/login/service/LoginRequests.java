package ua.edu.deanoffice.mobile.studentchdtu.user.login.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.model.Credentials;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.model.JWToken;

public interface LoginRequests {

    @POST("api/authenticate")
    Call<JWToken> requestAuthStudent(@Body Credentials cred);
}
