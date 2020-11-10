package ua.edu.deanoffice.mobile.studentchdtu.user.profile.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.Student;

public interface ProfileRequests {

    @GET("students")
    Call<Student> requestStudentInfo(@Header("Authorization") String token);
}
