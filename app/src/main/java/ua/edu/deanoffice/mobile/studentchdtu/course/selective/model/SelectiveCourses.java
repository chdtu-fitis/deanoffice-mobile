package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

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

    public int[] getIdsFirstSemester() {
        int [] ids = new int [selectiveCoursesFirstSemester.size()];
        int i = 0;
        for (SelectiveCourse selectiveCourse:selectiveCoursesFirstSemester) {
            ids[i] = selectiveCourse.getId();
            i++;
        }
        return ids;
    }

    public int[] getIdsSecondSemester() {
        int [] ids = new int [selectiveCoursesSecondSemester.size()];
        int i = 0;
        for (SelectiveCourse selectiveCourse:selectiveCoursesSecondSemester) {
            ids[i] = selectiveCourse.getId();
            i++;
        }
        return ids;
    }

}
