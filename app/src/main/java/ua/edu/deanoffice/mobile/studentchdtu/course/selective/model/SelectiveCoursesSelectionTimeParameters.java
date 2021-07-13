package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import androidx.annotation.Keep;

import lombok.Data;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.CourseSelectionPeriod;

@Data
@Keep
public class SelectiveCoursesSelectionTimeParameters {
    private CourseSelectionPeriod courseSelectionPeriod;
    private int studyYear;
    private long timeLeftUntilCurrentRoundEnd;
    private int generalMinStudentsCount;
    private int professionalMinStudentsCount;
    private int maxStudentsCount;
}
