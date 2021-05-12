package ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.Fragment;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.BaseDrawerActivity;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.fragment.StudentInformationFragment;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.Student;

public class StudentInformationActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Load xml for activity
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_student_information, mainContentBlock, false);
        mainContentBlock.addView(contentView, 1);

        getSupportActionBar().setTitle(getRString(R.string.action_bar_title_student_info));

        loadContent();
    }

    private void loadContent() {
        Student student = App.getInstance().getCurrentStudent();
        Fragment studentInformationFragment = new StudentInformationFragment(student);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, studentInformationFragment)
                .commit();
    }
}