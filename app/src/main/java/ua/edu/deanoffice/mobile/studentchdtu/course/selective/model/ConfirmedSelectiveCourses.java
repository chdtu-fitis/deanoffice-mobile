package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

public class ConfirmedSelectiveCourses {
    private int studentDegreeId;
    private int[] courseIdsForFirstSemester;
    private int[] courseIdsForSecondSemester;

    public int getStudentDegreeId() {
        return studentDegreeId;
    }

    public void setStudentDegreeId(int studentDegreeId) {
        this.studentDegreeId = studentDegreeId;
    }

    public int[] getCourseIdsForFirstSemester() {
        return courseIdsForFirstSemester;
    }

    public void setCourseIdsForFirstSemester(int[] courseIdsForFirstSemester) {
        this.courseIdsForFirstSemester = courseIdsForFirstSemester;
    }

    public int[] getCourseIdsForSecondSemester() {
        return courseIdsForSecondSemester;
    }

    public void setCourseIdsForSecondSemester(int[] courseIdsForSecondSemester) {
        this.courseIdsForSecondSemester = courseIdsForSecondSemester;
    }
}
