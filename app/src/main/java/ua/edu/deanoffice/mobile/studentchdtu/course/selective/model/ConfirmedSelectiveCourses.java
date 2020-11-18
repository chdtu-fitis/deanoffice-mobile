package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;
import java.util.List;

public class ConfirmedSelectiveCourses {
    private List<Integer> selectiveCourses;
    private int studentDegreeId;

    public List<Integer> getSelectiveCourses() {
        return selectiveCourses;
    }

    public void setSelectiveCourses(List<Integer> selectiveCourses) {
        this.selectiveCourses = selectiveCourses;
    }

    public int getStudentDegreeId() {
        return studentDegreeId;
    }

    public void setStudentDegreeId(int studentDegreeId) {
        this.studentDegreeId = studentDegreeId;
    }
}
