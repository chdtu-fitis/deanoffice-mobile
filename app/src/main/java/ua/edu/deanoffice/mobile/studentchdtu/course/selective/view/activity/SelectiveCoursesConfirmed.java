package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.ChdtuAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity.MainMenuActivity;

public class SelectiveCoursesConfirmed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selective_courses_confirmed);
        Bundle bundle = getIntent().getExtras();
        String coursesJson = bundle.getString("courses");
        SelectiveCourses selectiveCourses = new Gson().fromJson(coursesJson, SelectiveCourses.class);
        RecyclerView recyclerView = findViewById(R.id.listview1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ChdtuAdapter adapter = new ChdtuAdapter(selectiveCourses, getSupportFragmentManager(), null, true);
        recyclerView.setAdapter(adapter);
        adapter.disableCheckBoxes();
        Button selectiveCoursesButton = findViewById(R.id.menu_selectivecourses);
        selectiveCoursesButton.setOnClickListener((viewClear) -> {
            Intent intent = new Intent(SelectiveCoursesConfirmed.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
        });
    }
}