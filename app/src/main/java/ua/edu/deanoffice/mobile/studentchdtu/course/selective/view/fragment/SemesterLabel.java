package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.Semester;

public class SemesterLabel {
    private final Semester semester;

    public SemesterLabel(Semester semester) {
        this.semester = semester;
    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(Context context, ViewGroup container) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_semester, container, false);
        TextView semesterLabel = view.findViewById(R.id.semesterNumber);
        semesterLabel.setText((semester == Semester.FIRST ? "1" : "2") + " семестр");
        return view;
    }
}
