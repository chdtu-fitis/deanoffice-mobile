package ua.edu.deanoffice.mobile.studentchdtu.course.selective;

import android.content.Context;
import android.widget.TextView;

import lombok.Getter;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCoursesSelectionTimeParameters;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.Semester;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.StudentDegree;

public class SelectedCoursesCounter {
    @Getter
    private int selectedFirstSemester, selectedSecondSemester;
    @Getter
    private final int maxCoursesFirstSemester, maxCoursesSecondSemester, maxStudentsCount;

    private final TextView textView;
    private SelectListener selectListener = null;
    @Getter
    private Semester selectedSemester = Semester.FIRST;
    private boolean didAllCoursesSelected = false;

    public SelectedCoursesCounter(TextView textView, SelectiveCoursesSelectionTimeParameters timeParams) {
        this.textView = textView;
        this.maxCoursesFirstSemester = 3;
        this.maxCoursesSecondSemester = 2;
        this.maxStudentsCount = timeParams.getMaxStudentsCount();
    }

    public void init() {
        update();
    }

    public void incrementFirstSemester() {
        if (selectedFirstSemester >= maxCoursesFirstSemester) return;
        selectedFirstSemester++;
        update();
    }

    public void incrementSecondSemester() {
        if (selectedSecondSemester >= maxCoursesSecondSemester) return;
        selectedSecondSemester++;
        update();
    }

    public void decrementFirstSemester() {
        if (selectedFirstSemester <= 0) return;
        selectedFirstSemester--;
        update();
    }

    public void decrementSecondSemester() {
        if (selectedSecondSemester <= 0) return;
        selectedSecondSemester--;
        update();
    }

    public void setSelectedFirstSemester(int value) {
        if (value < 0 || value > maxCoursesFirstSemester) return;

        selectedFirstSemester = value;
        update();
    }

    public void setSelectedSecondSemester(int value) {
        if (value < 0 || value > maxCoursesSecondSemester) return;

        selectedSecondSemester = value;
        update();
    }

    public boolean isFirstSemesterFull() {
        return selectedFirstSemester == maxCoursesFirstSemester;
    }

    public boolean isSecondSemesterFull() {
        return selectedSecondSemester == maxCoursesSecondSemester;
    }

    public boolean confirmIsAvailable() {
        return selectedFirstSemester == maxCoursesFirstSemester && selectedSecondSemester == maxCoursesSecondSemester;
    }

    public boolean hasAllSelected() {
        return selectedFirstSemester == maxCoursesFirstSemester &&
                selectedSecondSemester == maxCoursesSecondSemester;
    }

    public void switchSemester(Semester semester) {
        this.selectedSemester = semester;
        update();
    }

    private void onSelectedCoursesCountChanged() {
        if (selectListener != null) {
            if (hasAllSelected()) {
                if (!didAllCoursesSelected) {
                    didAllCoursesSelected = true;
                    selectListener.onAllSelected();
                }
            } else {
                if (didAllCoursesSelected) {
                    didAllCoursesSelected = false;
                    selectListener.onNotAllSelected();
                }
            }
        }
    }

    private void update() {
        if (textView != null) {
            Context context = textView.getContext();
            String counterString = "";
            switch (selectedSemester) {
                case FIRST:
                    counterString = context.getResources().getString(R.string.header_selected_courses_counter_s1);
                    counterString = counterString.replace("{semester_1_count}", selectedFirstSemester + "");
                    counterString = counterString.replace("{semester_1_max}", maxCoursesFirstSemester + "");
                    break;
                case SECOND:
                    counterString = context.getResources().getString(R.string.header_selected_courses_counter_s2);
                    counterString = counterString.replace("{semester_2_count}", selectedSecondSemester + "");
                    counterString = counterString.replace("{semester_2_max}", maxCoursesSecondSemester + "");
                    break;
            }
            textView.setText(counterString);
            onSelectedCoursesCountChanged();
        }
    }

    public void setSelectListener(SelectListener selectListener) {
        this.selectListener = selectListener;
        onSelectedCoursesCountChanged();
    }

    public interface SelectListener {
        void onAllSelected();

        void onNotAllSelected();
    }
}
