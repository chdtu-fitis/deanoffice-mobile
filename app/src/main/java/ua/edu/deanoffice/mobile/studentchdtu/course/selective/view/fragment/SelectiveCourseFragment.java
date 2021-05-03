package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import lombok.Getter;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.Teacher;

public class SelectiveCourseFragment extends Fragment implements View.OnClickListener {
    public interface OnClickListener{
        boolean onClick(Object obj);
    }

    private final int layout;
    @Getter
    private final SelectiveCourse selectiveCourse;
    private CheckBox checkBox;
    private ImageView imageInfo;
    private Button btnCheckBox;
    private final boolean interactive;
    private final boolean showTrainingCycle;
    private final OnClickListener listener;
    private TextView textTeacherName, textDepartmentName, textStudentCount, disqualifiedLabel;
    private boolean selectedFromFirstRound = false;

    public SelectiveCourseFragment(SelectiveCourse selectiveCourse, int layout, OnClickListener listener, boolean interactive, boolean showTrainingCycle) {
        this.selectiveCourse = selectiveCourse;
        this.layout = layout;
        this.listener = listener;
        this.interactive = interactive;
        this.showTrainingCycle = showTrainingCycle;

        if (selectiveCourse.isSelected()) {
            selectedFromFirstRound = true;
        }
    }

    public void setCheckBoxInteractive(boolean interactive) {
        if (selectedFromFirstRound) {
            interactive = false;
        }
        checkBox.setClickable(interactive);
        btnCheckBox.setClickable(interactive);
    }

    public boolean isChecked() {
        return checkBox.isChecked();
    }

    public void setChecked(boolean checked) {
        if (selectedFromFirstRound) return;
        checkBox.setChecked(checked);
        selectiveCourse.setSelected(checked);
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

        textTeacherName = (TextView) view.findViewById(R.id.teacherName);
        if (selectiveCourse.getTeacher() != null) {
            textTeacherName.setText(selectiveCourse.getTeacher().getSurname() + " " + selectiveCourse.getTeacher().getName() + " " + selectiveCourse.getTeacher().getPatronimic());
        } else {
            ((LinearLayout) view.findViewById(R.id.textlayout)).removeView(textTeacherName);
        }

        textDepartmentName = (TextView) view.findViewById(R.id.departmentName);

        if (selectiveCourse.getDepartment() != null) {
            String facultyName = selectiveCourse.getDepartment().getFaculty().getAbbr();
            textDepartmentName.setText(facultyName + ", " + selectiveCourse.getDepartment().getName());
        }

        textStudentCount = (TextView) view.findViewById(R.id.studentCount);
        textStudentCount.setText(Integer.toString(selectiveCourse.getStudentsCount()));

        checkBox.setChecked(selectiveCourse.isSelected());

        imageInfo.setOnClickListener((viewClick) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(viewClick.getContext());
            ViewGroup viewGroup = viewClick.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(viewClick.getContext()).inflate(R.layout.dialog_selectivecourse_info, viewGroup, false);

            ((TextView) dialogView.findViewById(R.id.selectiveCourseName)).setText(selectiveCourse.getCourse().getCourseName().getName());
            ((TextView) dialogView.findViewById(R.id.selectiveCourseFaculty)).setText(selectiveCourse.getDepartment().getFaculty().getName());
            ((TextView) dialogView.findViewById(R.id.selectiveCourseDepartment)).setText(selectiveCourse.getDepartment().getName());
            ((TextView) dialogView.findViewById(R.id.selectiveCourseStudentCount)).setText("Кількість записаних студентів: " + selectiveCourse.getStudentsCount());
            Teacher teacher = selectiveCourse.getTeacher();
            String teacherFullName = teacher.getSurname() + " " + teacher.getName() + " " + teacher.getPatronimic();
            ((TextView) dialogView.findViewById(R.id.selectiveCourseTeacher)).setText(teacherFullName + " (" + teacher.getPosition().getName() + ")");

            ((TextView) dialogView.findViewById(R.id.selectiveCourseDescription)).setText(selectiveCourse.getDescription());
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            dialogView.findViewById(R.id.buttonOk).setOnClickListener((viewOk) -> {
                alertDialog.dismiss();
            });
        });

        btnCheckBox = view.findViewById(R.id.buttonBox);
        btnCheckBox.setOnClickListener(this);

        setCheckBoxInteractive(interactive);
    }

    public void setShortView() {
        textTeacherName.setVisibility(View.GONE);
        textDepartmentName.setVisibility(View.GONE);
        textStudentCount.setVisibility(View.GONE);
    }

    public void setExtendedView() {
        textTeacherName.setVisibility(View.VISIBLE);
        textDepartmentName.setVisibility(View.VISIBLE);
        textStudentCount.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        boolean isSuccess = listener.onClick(this);
        if(isSuccess){
            checkBox.setChecked(!checkBox.isChecked());
            selectiveCourse.setSelected(checkBox.isChecked());
        }
    }
}
