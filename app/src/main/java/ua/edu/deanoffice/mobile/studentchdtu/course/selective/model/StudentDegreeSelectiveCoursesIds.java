package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import java.util.List;

public class StudentDegreeSelectiveCoursesIds {
    private ExistingId studentDegree;
    private List<SelectiveCourseId> selectiveCourses;

    public ExistingId getStudentDegree() {
        return studentDegree;
    }

    public void setStudentDegree(ExistingId studentDegree) {
        this.studentDegree = studentDegree;
    }

    public List<SelectiveCourseId> getSelectiveCourses() {
        return selectiveCourses;
    }

    public void setSelectiveCourses(List<SelectiveCourseId> selectiveCourses) {
        this.selectiveCourses = selectiveCourses;
    }
}
