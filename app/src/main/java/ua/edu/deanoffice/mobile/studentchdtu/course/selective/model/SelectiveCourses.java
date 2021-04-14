package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.ValidModel;

@Getter
@Setter
public class SelectiveCourses extends ValidModel {

    private List<SelectiveCourse> selectiveCoursesFirstSemester;
    private List<SelectiveCourse> selectiveCoursesSecondSemester;
    private SelectiveCoursesProcessData selectiveCoursesProcessData;

    public List<SelectiveCourse> getSelectiveCoursesSemester(int semester) {
        return semester == 1 ? getSelectiveCoursesFirstSemester() : getSelectiveCoursesSecondSemester();
    }

    public List<Integer> getSelectiveCoursesIds() {
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