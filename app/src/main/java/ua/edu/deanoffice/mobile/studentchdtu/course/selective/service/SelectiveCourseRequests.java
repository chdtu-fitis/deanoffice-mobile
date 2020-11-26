package ua.edu.deanoffice.mobile.studentchdtu.course.selective.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ConfirmedSelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCoursesStudentDegree;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.StudentDegreeSelectiveCoursesIds;

public interface SelectiveCourseRequests {

    @GET("selective-courses")
    Call<SelectiveCourses> requestSelectiveCourses(@Header("Authorization") String token, @Query("studentDegreeId") int id);

    @GET("selective-courses/student-degree")
    Call<SelectiveCoursesStudentDegree> studentDegree(@Header("Authorization") String token, @Query("studentDegreeId") int id);

    @POST("selective-courses/registration")
    Call<StudentDegreeSelectiveCoursesIds> confirmedSelectiveCourses(@Header("Authorization") String token, @Body ConfirmedSelectiveCourses selectiveCourses);

}
