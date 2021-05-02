package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import java.util.List;

import lombok.Data;

@Data
public class SelectiveCoursesStudentDegree {
    private ExistingId studentDegree;
    private List<SelectiveCourse> selectiveCourses;
    private SelectiveCoursesSelectionTimeParameters selectiveCoursesSelectionTimeParameters;
}
