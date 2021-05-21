package ua.edu.deanoffice.mobile.studentchdtu.course.selective;

import android.content.Context;
import android.widget.TextView;

import java.util.Map;

import lombok.Getter;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCoursesSelectionRules;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCoursesSelectionTimeParameters;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.Semester;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.TypeCycle;

public class SelectedCoursesCounter {
    //Index 0 - Professions courses;
    //Index 1 - Generals courses;
    @Getter
    private final int[] selectedCountFirstSemester;
    @Getter
    private final int[] selectedCountSecondSemester;
    @Getter
    private final int[] needCoursesFirstSemester, needCoursesSecondSemester;
    @Getter
    private final int maxStudentsCount;

    private final TextView textViewSemester, textViewGeneralCounter, textViewProfessionalCounter;
    private final TextView countOfCourseView;

    private SelectListener selectListener = null;

    @Getter
    private Semester selectedSemester = Semester.FIRST;
    private boolean didAllCoursesSelected = false;

    public TextView getCountOfCourseView() {
        return countOfCourseView;
    }

    public SelectedCoursesCounter(Map<String, TextView> textViews, SelectiveCoursesSelectionTimeParameters timeParams, SelectiveCoursesSelectionRules[] selectionRules) {
        this.textViewSemester = textViews.get("Semester");
        this.textViewGeneralCounter = textViews.get("GeneralCounter");
        this.textViewProfessionalCounter = textViews.get("ProfessionalCounter");
        this.countOfCourseView = textViews.get("ViewControlOfCourses");

        //Professional
        this.needCoursesFirstSemester = new int[2];
        this.needCoursesFirstSemester[0] = selectionRules[0].getSelectiveCoursesNumber()[0];
        this.needCoursesFirstSemester[1] = selectionRules[1].getSelectiveCoursesNumber()[0];
        //General
        this.needCoursesSecondSemester = new int[2];
        this.needCoursesSecondSemester[0] = selectionRules[0].getSelectiveCoursesNumber()[1];
        this.needCoursesSecondSemester[1] = selectionRules[1].getSelectiveCoursesNumber()[1];
        this.maxStudentsCount = timeParams.getMaxStudentsCount();

        this.selectedCountFirstSemester = new int[2];
        this.selectedCountSecondSemester = new int[2];
    }

    public void init() {
        update();
    }

    public void setSelectedCountFirstSemester(int[] value) {
        if (value[0] < 0 || value[1] < 0 || value[0] > needCoursesFirstSemester[0] || value[1] > needCoursesFirstSemester[1])
            return;

        selectedCountFirstSemester[0] = value[0];
        selectedCountFirstSemester[1] = value[1];
        update();
    }

    public void setSelectedCountSecondSemester(int[] value) {
        if (value[0] < 0 || value[1] < 0 || value[0] > needCoursesSecondSemester[0] || value[1] > needCoursesSecondSemester[1])
            return;

        selectedCountSecondSemester[0] = value[0];
        selectedCountSecondSemester[1] = value[1];
        update();
    }

    public boolean isFirstSemesterFull(TypeCycle cycle) {
        boolean result;
        if (cycle == TypeCycle.GENERAL) {
            result = selectedCountFirstSemester[1] == needCoursesFirstSemester[1];
        } else {
            result = selectedCountFirstSemester[0] == needCoursesFirstSemester[0];
        }
        return result;
    }

    public boolean isSecondSemesterFull(TypeCycle cycle) {
        boolean result;
        if (cycle == TypeCycle.GENERAL) {
            result = selectedCountSecondSemester[1] == needCoursesSecondSemester[1];
        } else {
            result = selectedCountSecondSemester[0] == needCoursesSecondSemester[0];
        }
        return result;
    }

    public boolean hasAllSelected() {
        return selectedCountFirstSemester[0] == needCoursesFirstSemester[0] && selectedCountFirstSemester[1] == needCoursesFirstSemester[1]
                && selectedCountSecondSemester[0] == needCoursesSecondSemester[0] && selectedCountSecondSemester[1] == needCoursesSecondSemester[1];
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
        if (textViewSemester == null || textViewGeneralCounter == null || textViewProfessionalCounter == null) {
            return;
        }

        Context context = textViewSemester.getContext();
        String semesterString = "", generalCounterString, professionalCounterString;

        generalCounterString = context.getResources().getString(R.string.header_selected_courses_counter_general);
        professionalCounterString = context.getResources().getString(R.string.header_selected_courses_counter_professional);

        switch (selectedSemester) {
            case FIRST:
                semesterString = context.getResources().getString(R.string.header_selected_courses_s1);
                professionalCounterString = professionalCounterString.replace("{professional_count}", selectedCountFirstSemester[0] + "");
                professionalCounterString = professionalCounterString.replace("{professional_max}", needCoursesFirstSemester[0] + "");

                generalCounterString = generalCounterString.replace("{general_count}", selectedCountFirstSemester[1] + "");
                generalCounterString = generalCounterString.replace("{general_max}", needCoursesFirstSemester[1] + "");
                break;
            case SECOND:
                semesterString = context.getResources().getString(R.string.header_selected_courses_s2);
                professionalCounterString = professionalCounterString.replace("{professional_count}", selectedCountSecondSemester[0] + "");
                professionalCounterString = professionalCounterString.replace("{professional_max}", needCoursesSecondSemester[0] + "");

                generalCounterString = generalCounterString.replace("{general_count}", selectedCountSecondSemester[1] + "");
                generalCounterString = generalCounterString.replace("{general_max}", needCoursesSecondSemester[1] + "");
                break;
        }

        textViewSemester.setText(semesterString);
        textViewGeneralCounter.setText(generalCounterString);
        textViewProfessionalCounter.setText(professionalCounterString);
        onSelectedCoursesCountChanged();
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
