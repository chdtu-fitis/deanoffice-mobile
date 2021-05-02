package ua.edu.deanoffice.mobile.studentchdtu.course.selective;

import android.content.Context;
import android.widget.TextView;

import lombok.Getter;
import ua.edu.deanoffice.mobile.studentchdtu.R;

public class SelectedCoursesCounter {
    @Getter
    private int selectedFirstSemester, selectedSecondSemester;
    @Getter
    private final int maxCoursesFirstSemester, maxCoursesSecondSemester;

    private final TextView textView;

    public SelectedCoursesCounter(TextView textView, int maxCoursesFirstSemester, int maxCoursesSecondSemester) {
        this.textView = textView;
        this.maxCoursesFirstSemester = maxCoursesFirstSemester;
        this.maxCoursesSecondSemester = maxCoursesSecondSemester;
    }

    public void init(){
        updateTextView();
    }

    public void incrementFirstSemester() {
        selectedFirstSemester++;
        updateTextView();
    }

    public void incrementSecondSemester() {
        selectedSecondSemester++;
        updateTextView();
    }

    public void decrementFirstSemester() {
        selectedFirstSemester--;
        updateTextView();
    }

    public void decrementSecondSemester() {
        selectedSecondSemester--;
        updateTextView();
    }

    private void updateTextView() {
        if (textView != null) {
            Context context = textView.getContext();
            String counterString = context.getResources().getString(R.string.header_selected_courses_counter);
            counterString = counterString.replace("{semester_1_count}", selectedFirstSemester + "");
            counterString = counterString.replace("{semester_1_max}", maxCoursesFirstSemester + "");
            counterString = counterString.replace("{semester_2_count}", selectedSecondSemester + "");
            counterString = counterString.replace("{semester_2_max}", maxCoursesSecondSemester + "");

            textView.setText(counterString);
        }
    }
}
