package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import java.util.ArrayList;
import java.util.List;

import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.ValidModel;

public class SelectiveCourses extends ValidModel {

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

    public List<Integer> getSelectiveCoursesId() {
        List<Integer> selectiveCoursesIds = new ArrayList<>();
        for (SelectiveCourse selectiveCourse : selectiveCoursesFirstSemester) {
            selectiveCoursesIds.add(selectiveCourse.getId());
        }

        for (SelectiveCourse selectiveCourse : selectiveCoursesSecondSemester) {
            selectiveCoursesIds.add(selectiveCourse.getId());
        }
        return selectiveCoursesIds;
    }

}