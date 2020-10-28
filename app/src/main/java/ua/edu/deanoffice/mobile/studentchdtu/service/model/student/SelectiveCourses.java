package ua.edu.deanoffice.mobile.studentchdtu.service.model.student;

import java.util.List;

import ua.edu.deanoffice.mobile.studentchdtu.service.model.course.SelectiveCourse;

public class SelectiveCourses extends ValidModel{

    private List<SelectiveCourse> selectiveCoursesFirstSemester;
    private List<SelectiveCourse> selectiveCoursesSecondSemester;

    public List<SelectiveCourse> getSelectiveCoursesFirstSemester() {
        return selectiveCoursesFirstSemester;
    }

    public void setSelectiveCoursesFirstSemester(List<SelectiveCourse> selectiveCoursesFirstSemester) {
        this.selectiveCoursesFirstSemester = selectiveCoursesFirstSemester;
    }

    public List<SelectiveCourse> getSelectiveCoursesSecondSemester() {
        return selectiveCoursesSecondSemester;
    }

    public void setSelectiveCoursesSecondSemester(List<SelectiveCourse> selectiveCoursesSecondSemester) {
        this.selectiveCoursesSecondSemester = selectiveCoursesSecondSemester;
    }

    public List<SelectiveCourse> getSelectiveCoursesSemester(int semester) {
        return semester == 1 ? getSelectiveCoursesFirstSemester() : getSelectiveCoursesSecondSemester();
    }
}
