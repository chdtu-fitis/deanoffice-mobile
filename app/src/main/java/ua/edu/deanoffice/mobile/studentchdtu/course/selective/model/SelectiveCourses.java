package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.ValidModel;

@Data
@Keep
public class SelectiveCourses extends ValidModel {
    private List<SelectiveCourse> selectiveCoursesFirstSemester;
    private List<SelectiveCourse> selectiveCoursesSecondSemester;
    private SelectiveCoursesSelectionTimeParameters selectiveCoursesSelectionTimeParameters;
    private SelectiveCoursesSelectionRules[] selectiveCoursesSelectionRules;

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

    public List<SelectiveCourse> getSelectiveCoursesBothSemesters() {
        List<SelectiveCourse> selectiveCourses = new ArrayList<>();
        selectiveCourses.addAll(selectiveCoursesFirstSemester);
        selectiveCourses.addAll(selectiveCoursesSecondSemester);
        return selectiveCourses;
    }
}