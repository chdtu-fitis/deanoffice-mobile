package ua.edu.deanoffice.mobile.studentchdtu.course.selective.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;

public interface SelectiveCourseRequests {

    @GET("selective-courses")
    Call<SelectiveCourses> requestSelectiveCourses(@Header("Authorization") String token, @Query("studentDegreeId") int id);
}
