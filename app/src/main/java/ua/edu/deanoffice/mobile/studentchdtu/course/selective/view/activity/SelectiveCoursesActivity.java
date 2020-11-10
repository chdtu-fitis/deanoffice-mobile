package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.service.SelectiveCourseRequests;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity.MainMenuActivity;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.ChdtuAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;

public class SelectiveCoursesActivity extends AppCompatActivity {

    private TextView selectiveCoursesCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selective_courses_test);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_selectivecourses);

        selectiveCoursesCounter = findViewById(R.id.text_body);

        App.getInstance().getClient().createRequest(SelectiveCourseRequests.class)
                .requestSelectiveCourses(App.getInstance().getJwt().getToken(),
                App.getInstance().getCurrentStudent().getDegrees()[0].getId()).enqueue(new Callback<SelectiveCourses>() {
            @Override
            public void onResponse(Call<SelectiveCourses> call, Response<SelectiveCourses> response) {
                if (response.isSuccessful()) {
                    SelectiveCoursesActivity.this.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<SelectiveCourses> call, Throwable t) {

            }
        });
    }

    public void onResponse(SelectiveCourses selectiveCourses) {
        runOnUiThread(() -> {
            RecyclerView recyclerView = findViewById(R.id.listview);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            ChdtuAdapter adapter = new ChdtuAdapter(selectiveCourses, getSupportFragmentManager(), selectiveCoursesCounter, true);

            recyclerView.setAdapter(adapter);

            Button clearBtn = findViewById(R.id.clear_selectivecourses);
            clearBtn.setOnClickListener((view) -> {
                adapter.clearSelected();
            });

            Button confirmBtn = findViewById(R.id.confirm_selectivecourses);
            confirmBtn.setOnClickListener((view) -> {
                if (adapter.getSelectedCourseFirstSemester().size() == 3 && adapter.getSelectiveCourseSecondSemester().size() == 2) {
                    selectiveCoursesCounter.setText("Підтвердіть вибрані дисципліни");
                    confirmBtn.setText("Підтвердити");
                    clearBtn.setText("Скасувати");

                    confirmBtn.setOnClickListener((viewConfirm) -> {
                        Intent intent = new Intent(SelectiveCoursesActivity.this, MainMenuActivity.class);
                        startActivity(intent);
                        finish();
                    });

                    clearBtn.setOnClickListener((viewClear) -> {
                        Intent intent = new Intent(SelectiveCoursesActivity.this, SelectiveCoursesActivity.class);
                        startActivity(intent);
                        finish();
                    });

                    SelectiveCourses selectiveCoursesFinal = new SelectiveCourses();
                    selectiveCoursesFinal.setSelectiveCoursesFirstSemester(adapter.getSelectedCourseFirstSemester());
                    selectiveCoursesFinal.setSelectiveCoursesSecondSemester(adapter.getSelectiveCourseSecondSemester());

                    ChdtuAdapter adapterFinal = new ChdtuAdapter(selectiveCoursesFinal, getSupportFragmentManager(), null, false);
                    recyclerView.setAdapter(adapterFinal);
                    adapterFinal.disableCheckBoxes();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Оберіть дисципліни", Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SelectiveCoursesActivity.this, MainMenuActivity.class);
        startActivity(intent);
        finish();
    }
}
