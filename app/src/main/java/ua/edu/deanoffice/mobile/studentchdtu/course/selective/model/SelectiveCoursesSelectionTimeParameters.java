package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import lombok.Data;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.CourseSelectionPeriod;

@Data
public class SelectiveCoursesSelectionTimeParameters {
    private CourseSelectionPeriod courseSelectionPeriod;
    private int studyYear;
    private long timeLeftUntilCurrentRoundEnd;
    private int generalMinStudentsCount;
    private int professionalMinStudentsCount;
    private int maxStudentsCount;
}
