package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ua.edu.deanoffice.mobile.studentchdtu.R;

public class SemesterFragment extends Fragment {

    private final int layout;
    private int semester;

    public SemesterFragment(int semester, int layout) {
        this.semester = semester;
        this.layout = layout;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        ((TextView)view.findViewById(R.id.semesterNumber)).setText(semester + " семестр");
    }
}
