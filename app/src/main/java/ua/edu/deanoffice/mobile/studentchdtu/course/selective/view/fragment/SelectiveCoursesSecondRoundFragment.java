package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import java.util.ArrayList;
import java.util.List;

import ua.edu.deanoffice.mobile.studentchdtu.course.selective.SelectedCoursesCounter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ConfirmedSelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCoursesSelectionTimeParameters;

public class SelectiveCoursesSecondRoundFragment extends BaseSelectiveCoursesFragment {
    private final SelectiveCourses selectedSelectiveCourses;

    public SelectiveCoursesSecondRoundFragment(SelectedCoursesCounter selectedCoursesCounter,
                                               SelectiveCourses availableSelectiveCourses,
                                               SelectiveCourses selectedSelectiveCourses) {
        super(selectedCoursesCounter);
        this.selectedSelectiveCourses = selectedSelectiveCourses;

        showingSelectiveCourses = null;
        if (selectedSelectiveCourses == null) {
            showingSelectiveCourses = availableSelectiveCourses;
        } else if (availableSelectiveCourses != null) {
            showingSelectiveCourses = uniteAvailableAndSelectedCourses(availableSelectiveCourses, selectedSelectiveCourses);
        }
    }

    public SelectiveCoursesSecondRoundFragment(SelectedCoursesCounter selectedCoursesCounter,
                                               SelectiveCourses availableCourses) {
        this(selectedCoursesCounter, availableCourses, null);
    }

    private SelectiveCourses uniteAvailableAndSelectedCourses(SelectiveCourses availableSelectiveCourses,
                                                              SelectiveCourses selectedSelectiveCourses) {
        SelectiveCourses resultSelectiveCourses = new SelectiveCourses();
        SelectiveCoursesSelectionTimeParameters timeParameters = availableSelectiveCourses.getSelectiveCoursesSelectionTimeParameters();

        resultSelectiveCourses.setSelectiveCoursesSelectionTimeParameters(timeParameters);

        if (selectedSelectiveCourses != null) {
            List<SelectiveCourse> selectiveCoursesFirstSemester;
            List<SelectiveCourse> selectiveCoursesSecondSemester;
            selectiveCoursesFirstSemester = unitSemesterAvailableAndSelectedCourses(
                    availableSelectiveCourses.getSelectiveCoursesFirstSemester(),
                    selectedSelectiveCourses.getSelectiveCoursesFirstSemester()
            );
            selectiveCoursesSecondSemester = unitSemesterAvailableAndSelectedCourses(
                    availableSelectiveCourses.getSelectiveCoursesSecondSemester(),
                    selectedSelectiveCourses.getSelectiveCoursesSecondSemester()
            );
            resultSelectiveCourses.setSelectiveCoursesFirstSemester(selectiveCoursesFirstSemester);
            resultSelectiveCourses.setSelectiveCoursesSecondSemester(selectiveCoursesSecondSemester);
        } else {
            resultSelectiveCourses = availableSelectiveCourses;
        }

        return resultSelectiveCourses;
    }

    private List<SelectiveCourse> unitSemesterAvailableAndSelectedCourses(List<SelectiveCourse> availableSelectiveCourseList,
                                                                          List<SelectiveCourse> selectedSelectiveCourseList) {
        List<SelectiveCourse> resultCoursesList = new ArrayList<>();
        resultCoursesList.addAll(availableSelectiveCourseList);
        for (SelectiveCourse selectedCourse : selectedSelectiveCourseList) {
            boolean elementIncluded = false;
            for (SelectiveCourse availableCourse : availableSelectiveCourseList) {
                if (selectedCourse.getId() == availableCourse.getId()) {
                    availableCourse.setSelected(true);
                    elementIncluded = true;
                    break;
                }
            }
            if (!elementIncluded) {
                resultCoursesList.add(selectedCourse);
            }
        }

        return resultCoursesList;
    }

    @Override
    protected ConfirmedSelectiveCourses getConfirmedSelectiveCourses(SelectiveCourses selectiveCourses) {
        ConfirmedSelectiveCourses confirmedSelectiveCourses = new ConfirmedSelectiveCourses();

        if (selectedSelectiveCourses != null) {
            List<Integer> newSelectedCourses = new ArrayList<>();
            for (Integer courseId : selectiveCourses.getSelectiveCoursesIds()) {
                boolean elementIncluded = false;
                for (Integer existingCourseId : selectedSelectiveCourses.getSelectiveCoursesIds()) {
                    if (courseId.equals(existingCourseId)) {
                        elementIncluded = true;
                        break;
                    }
                }
                if (!elementIncluded) {
                    newSelectedCourses.add(courseId);
                }
            }
            confirmedSelectiveCourses.setSelectiveCourses(newSelectedCourses);
        } else {
            confirmedSelectiveCourses.setSelectiveCourses(selectiveCourses.getSelectiveCoursesIds());
        }
        return confirmedSelectiveCourses;
    }
}