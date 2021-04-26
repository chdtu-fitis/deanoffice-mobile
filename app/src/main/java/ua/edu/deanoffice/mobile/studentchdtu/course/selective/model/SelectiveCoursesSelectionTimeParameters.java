package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.CourseSelectionPeriod;

public class SelectiveCoursesSelectionTimeParameters {
    private CourseSelectionPeriod courseSelectionPeriod;
    private int studyYear;
    private long timeLeftUntilCurrentRoundEnd;

    public CourseSelectionPeriod getCourseSelectionPeriod() {
        return courseSelectionPeriod;
    }

    public void setCourseSelectionPeriod(CourseSelectionPeriod courseSelectionPeriod) {
        this.courseSelectionPeriod = courseSelectionPeriod;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public long getTimeLeftUntilCurrentRoundEnd() {
        return timeLeftUntilCurrentRoundEnd;
    }

    public void setTimeLeftUntilCurrentRoundEnd(long timeLeftUntilCurrentRoundEnd) {
        this.timeLeftUntilCurrentRoundEnd = timeLeftUntilCurrentRoundEnd;
    }
}
