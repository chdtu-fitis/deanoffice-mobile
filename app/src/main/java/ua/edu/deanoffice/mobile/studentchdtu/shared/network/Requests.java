package ua.edu.deanoffice.mobile.studentchdtu.shared.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ua.edu.deanoffice.mobile.studentchdtu.applications.model.Application;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.model.Credentials;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.model.JWToken;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.Student;

public interface Requests {

    @GET("applications")
    Call<Application> requestStudentInfo(@Query("applicationType") int id, @Query("json") String json);

    @GET("application-types")
    Call<ResponseBody> requestApplicationType();

    @GET("selective-courses")
    Call<SelectiveCourses> requestSelectiveCourses(@Header("Authorization") String token, @Query("studentDegreeId") int id);

    @GET("students")
    Call<Student> requestStudentInfo(@Header("Authorization") String token);

    @POST("api/authenticate")
    Call<JWToken> requestAuthStudent(@Body Credentials cred);
}
