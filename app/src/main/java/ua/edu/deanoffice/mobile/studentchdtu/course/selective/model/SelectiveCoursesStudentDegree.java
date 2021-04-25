package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectiveCoursesStudentDegree {
    private ExistingId studentDegree;
    private List<SelectiveCourse> selectiveCourses;
    private SelectiveCoursesSelectionTimeParameters selectiveCoursesSelectionTimeParameters;
}
