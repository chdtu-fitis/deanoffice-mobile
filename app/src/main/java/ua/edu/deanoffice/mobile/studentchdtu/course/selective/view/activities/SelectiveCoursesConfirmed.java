package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.ChdtuAdapter;

public class SelectiveCoursesConfirmed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selective_courses_confirmed);
        Bundle bundle = getIntent().getExtras();
        String coursesJson = bundle.getString("courses");
        SelectiveCourses selectiveCourses = new Gson().fromJson(coursesJson, SelectiveCourses.class);
        RecyclerView recyclerView = findViewById(R.id.listview1);
        ChdtuAdapter adapter = new ChdtuAdapter(selectiveCourses, getSupportFragmentManager(), null, false);
        recyclerView.setAdapter(adapter);
        adapter.disableCheckBoxes();
    }
}