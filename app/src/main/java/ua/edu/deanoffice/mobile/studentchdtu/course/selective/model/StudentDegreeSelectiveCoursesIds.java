package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import java.util.List;

import lombok.Data;

@Data
public class StudentDegreeSelectiveCoursesIds {
    private ExistingId studentDegree;
    private List<SelectiveCourseId> selectiveCourses;
}
