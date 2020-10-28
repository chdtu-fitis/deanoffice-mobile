package ua.edu.deanoffice.mobile.studentchdtu.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.activities.views.ChdtuAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.Mobile;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.Client;
import ua.edu.deanoffice.mobile.studentchdtu.service.model.course.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.service.model.student.SelectiveCourses;

public class SelectiveCoursesTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selective_courses_test);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.selectivecourses_actionbar);

        Mobile.getInstance().getClient().getSelectiveCourses(new Client.OnResponseCallback() {
            @Override
            public void onResponseSuccess(ResponseBody response) {
                onResponse(response);
            }

            @Override
            public void onResponseFailure(ResponseBody response) {
                Log.d("Test", "Error");
            }
        });
    }


    public void onResponse(ResponseBody response) {
        runOnUiThread(() -> {
            try {
                SelectiveCourses selectiveCourses = new Gson().fromJson(response.string(), SelectiveCourses.class);

                RecyclerView recyclerView = findViewById(R.id.listview);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);

                ChdtuAdapter adapter = new ChdtuAdapter(selectiveCourses, getSupportFragmentManager());
                recyclerView.setAdapter(adapter);

                Button btn = findViewById(R.id.confirm_selectivecourses);
                btn.setOnClickListener((view) -> {

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
