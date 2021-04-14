package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectiveCoursesProcessData {
    private int currentRound;
    private int studyYear;
    private long timeLeftUntilCurrentRoundEnd;
}
