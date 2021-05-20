package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import ua.edu.deanoffice.mobile.studentchdtu.course.selective.SelectedCoursesCounter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;

public class SelectiveCoursesFragment extends BaseSelectiveCoursesFragment {
    public SelectiveCoursesFragment(SelectedCoursesCounter selectedCoursesCounter, SelectiveCourses availableSelectiveCourses) {
        super(availableSelectiveCourses, selectedCoursesCounter);
    }
}
