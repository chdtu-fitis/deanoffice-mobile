package ua.edu.deanoffice.mobile.studentchdtu.course.selective.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ua.edu.deanoffice.mobile.studentchdtu.BuildConfig;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ConfirmedSelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCoursesStudentDegree;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.StudentDegreeSelectiveCoursesIds;

public interface SelectiveCourseRequests {

    @GET("selective-courses")
    @Headers("X-App-Version: " + BuildConfig.VERSION_NAME)
    Call<SelectiveCourses> requestSelectiveCourses(@Header("Authorization") String token, @Query("studentDegreeId") int id, @Query("studentsCount") boolean studentCount);

    @GET("selective-courses/student-degree")
    @Headers("X-App-Version: " + BuildConfig.VERSION_NAME)
    Call<SelectiveCoursesStudentDegree> studentDegree(@Header("Authorization") String token, @Query("studentDegreeId") int id, @Query("all") boolean all);

    @POST("selective-courses/registration")
    @Headers("X-App-Version: " + BuildConfig.VERSION_NAME)
    Call<StudentDegreeSelectiveCoursesIds> confirmedSelectiveCourses(@Header("Authorization") String token, @Body ConfirmedSelectiveCourses selectiveCourses);
}
