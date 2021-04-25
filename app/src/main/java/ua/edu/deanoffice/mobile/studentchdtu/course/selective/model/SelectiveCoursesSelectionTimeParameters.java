package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import lombok.Getter;
import lombok.Setter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.CourseSelectionPeriod;

@Getter
@Setter
public class SelectiveCoursesSelectionTimeParameters {
    private CourseSelectionPeriod courseSelectionPeriod;
    private int studyYear;
    private long timeLeftUntilCurrentRoundEnd;
}
