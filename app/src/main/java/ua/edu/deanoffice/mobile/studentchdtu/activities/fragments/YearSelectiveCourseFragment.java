package ua.edu.deanoffice.mobile.studentchdtu.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.service.model.course.SelectiveCourse;

public class YearSelectiveCourseFragment extends Fragment {
    private final int layout;
    private SelectiveCourse selectiveCourse;
    private CheckBox checkBox;
    private ImageView imageInfo;

    public YearSelectiveCourseFragment(SelectiveCourse selectiveCourse, int layout) {
        this.selectiveCourse = selectiveCourse;
        this.layout = layout;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        checkBox = view.findViewById(R.id.selectivecheckbox);
        imageInfo = view.findViewById(R.id.selectivecourseinfo);
        ((TextView)view.findViewById(R.id.selectivecoursename)).setText(selectiveCourse.getCourse().getCourseName().getName());

        imageInfo.setOnClickListener((viewClick) -> {

        });
    }

}
