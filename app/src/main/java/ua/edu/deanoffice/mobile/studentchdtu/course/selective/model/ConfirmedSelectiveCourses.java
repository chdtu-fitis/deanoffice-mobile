package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;
import java.util.List;

public class ConfirmedSelectiveCourses {
    private List<Integer> selectiveCourses;
    private ExistingId studentDegree;

    public List<Integer> getSelectiveCourses() {
        return selectiveCourses;
    }

    public void setSelectiveCourses(List<Integer> selectiveCourses) {
        this.selectiveCourses = selectiveCourses;
    }

    public ExistingId getStudentDegreeId() {
        return studentDegree;
    }

    public void setStudentDegreeId(ExistingId studentDegreeId) {
        this.studentDegree = studentDegreeId;
    }
}
