package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import java.util.List;

public class SelectiveCoursesStudentDegree {
    private ExistingId studentDegree;
    private List<SelectiveCourse> selectiveCourses;
    private SelectiveCoursesSelectionTimeParameters selectiveCoursesSelectionTimeParameters;

    public SelectiveCoursesStudentDegree() { }

    public ExistingId getStudentDegree() {
        return studentDegree;
    }

    public void setStudentDegree(ExistingId studentDegree) {
        this.studentDegree = studentDegree;
    }

    public List<SelectiveCourse> getSelectiveCourses() {
        return selectiveCourses;
    }

    public void setSelectiveCourses(List<SelectiveCourse> selectiveCourses) {
        this.selectiveCourses = selectiveCourses;
    }

    public SelectiveCoursesSelectionTimeParameters getSelectiveCoursesSelectionTimeParameters() {
        return selectiveCoursesSelectionTimeParameters;
    }

    public void setSelectiveCoursesSelectionTimeParameters(SelectiveCoursesSelectionTimeParameters selectiveCoursesSelectionTimeParameters) {
        this.selectiveCoursesSelectionTimeParameters = selectiveCoursesSelectionTimeParameters;
    }
}
