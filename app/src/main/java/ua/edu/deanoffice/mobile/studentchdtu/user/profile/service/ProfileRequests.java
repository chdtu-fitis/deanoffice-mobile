package ua.edu.deanoffice.mobile.studentchdtu.user.profile.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import ua.edu.deanoffice.mobile.studentchdtu.BuildConfig;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.Student;

public interface ProfileRequests {

    @GET("students")
    @Headers("X-App-Version: " + BuildConfig.VERSION_NAME)
    Call<Student> requestStudentInfo(@Header("Authorization") String token);
}
