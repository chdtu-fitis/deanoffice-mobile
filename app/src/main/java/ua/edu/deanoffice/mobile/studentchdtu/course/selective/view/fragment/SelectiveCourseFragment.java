package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;

public class SelectiveCourseFragment extends Fragment {

    private final int layout;
    private SelectiveCourse selectiveCourse;
    private CheckBox checkBox;
    private ImageView imageInfo;
    private Button btnCheckBox;
    private boolean interactive;
    private boolean showTrainingCycle;
    private View.OnClickListener listener;
    private TextView disqualifiedLabel;

    public SelectiveCourse getSelectiveCourse() {
        return selectiveCourse;
    }

    public void setCheckBoxInteractive(boolean interactive) {
        checkBox.setClickable(interactive);
        btnCheckBox.setClickable(interactive);
    }

    public boolean isChecked() {
        return checkBox.isChecked();
    }

    public void setChecked(boolean checked) {
        checkBox.setChecked(checked);
        selectiveCourse.selected = checked;
    }

    public SelectiveCourseFragment(SelectiveCourse selectiveCourse, int layout, View.OnClickListener listener, boolean interactive, boolean showTrainingCycle) {
        this.selectiveCourse = selectiveCourse;
        this.layout = layout;
        this.listener = listener;
        this.interactive = interactive;
        this.showTrainingCycle = showTrainingCycle;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        checkBox = view.findViewById(R.id.selectivecheckbox);
        imageInfo = view.findViewById(R.id.selectivecourseinfo);
        disqualifiedLabel = view.findViewById(R.id.labelDisqulifiedCourse);

        //Make disqualified
        if (!selectiveCourse.isAvailable()) {
            disqualifiedLabel.setVisibility(View.VISIBLE);
            view.setBackgroundColor(getResources().getColor(R.color.disqualified_course_fond, null));
        }

        if (showTrainingCycle) {
            if (selectiveCourse.getTrainingCycle().equals("GENERAL")) {
                ((TextView) view.findViewById(R.id.selectivecoursename)).setText(selectiveCourse.getCourse().getCourseName().getName() + " (Загальний рівень)");
            } else {
                ((TextView) view.findViewById(R.id.selectivecoursename)).setText(selectiveCourse.getCourse().getCourseName().getName() + " (Професійний рівень)");
            }
        } else {
            ((TextView) view.findViewById(R.id.selectivecoursename)).setText(selectiveCourse.getCourse().getCourseName().getName());
        }

        if (selectiveCourse.getTeacher() != null) {
            ((TextView) view.findViewById(R.id.teacherName)).setText(selectiveCourse.getTeacher().getSurname() + " " + selectiveCourse.getTeacher().getName() + " " + selectiveCourse.getTeacher().getPatronimic());
        } else {
            ((LinearLayout) view.findViewById(R.id.textlayout)).removeView(view.findViewById(R.id.teacherName));
        }

        checkBox.setChecked(selectiveCourse.selected);

        imageInfo.setOnClickListener((viewClick) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(viewClick.getContext());
            ViewGroup viewGroup = viewClick.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(viewClick.getContext()).inflate(R.layout.dialog_selectivecourse_info, viewGroup, false);

            ((TextView) dialogView.findViewById(R.id.selectiveCourseName)).setText(selectiveCourse.getCourse().getCourseName().getName());
            ((TextView) dialogView.findViewById(R.id.selectiveCourseDescription)).setText(selectiveCourse.getDescription());
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            ((Button) dialogView.findViewById(R.id.buttonOk)).setOnClickListener((viewOk) -> {
                alertDialog.dismiss();
            });
        });

        btnCheckBox = view.findViewById(R.id.buttonBox);
        btnCheckBox.setOnClickListener((viewButton) -> {
            checkBox.setChecked(!checkBox.isChecked());
            selectiveCourse.selected = checkBox.isChecked();
            listener.onClick(viewButton);
        });

        setCheckBoxInteractive(interactive);
    }
}
