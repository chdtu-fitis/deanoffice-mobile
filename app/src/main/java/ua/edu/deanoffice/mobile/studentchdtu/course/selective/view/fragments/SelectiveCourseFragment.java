package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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
    private View.OnClickListener listener;

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

    public SelectiveCourseFragment(SelectiveCourse selectiveCourse, int layout, View.OnClickListener listener, boolean interactive) {
        this.selectiveCourse = selectiveCourse;
        this.layout = layout;
        this.listener = listener;
        this.interactive = interactive;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        checkBox = view.findViewById(R.id.selectivecheckbox);
        imageInfo = view.findViewById(R.id.selectivecourseinfo);
        ((TextView) view.findViewById(R.id.selectivecoursename)).setText(selectiveCourse.getCourse().getCourseName().getName());
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
