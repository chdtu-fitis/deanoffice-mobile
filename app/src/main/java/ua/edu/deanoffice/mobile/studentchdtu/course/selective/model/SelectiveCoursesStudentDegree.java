package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import androidx.annotation.Keep;

import java.util.List;

import lombok.Data;

@Data
@Keep
public class SelectiveCoursesStudentDegree {
    private ExistingId studentDegree;
    private List<SelectiveCourse> selectiveCourses;
    private SelectiveCoursesSelectionTimeParameters selectiveCoursesSelectionTimeParameters;
}
