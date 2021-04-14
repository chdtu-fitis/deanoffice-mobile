package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.BaseDrawerActivity;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SelectiveCoursesFragment;

public class SelectiveCoursesActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_selective_courses, mainContentBlock, false);
        mainContentBlock.addView(contentView,1);

        getSupportActionBar().setTitle(getRString(R.string.action_bar_title_selective_courses));

        //Init views

        //Get selective courses
        //Get data time, it is  second round or first
        //Show selective courses to fragment

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SelectiveCoursesFragment()).commit();
/*
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
 */
    }
/*
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
                if (adapter.getSelectedCourseFirstSemester().size() == 3 && adapter.getSelectedCourseSecondSemester().size() == 2) {
                    selectiveCoursesCounter.setText("Підтвердіть обрані дисципліни");
                    confirmBtn.setText("Підтвердити");
                    clearBtn.setText("Скасувати");

                    clearBtn.setOnClickListener((viewClear) -> {
                        Intent intent = new Intent(SelectiveCoursesActivity.this, SelectiveCoursesActivity.class);
                        startActivity(intent);
                        finish();
                    });

                    SelectiveCourses selectiveCoursesFinal = new SelectiveCourses();
                    selectiveCoursesFinal.setSelectiveCoursesFirstSemester(adapter.getSelectedCourseFirstSemester());
                    selectiveCoursesFinal.setSelectiveCoursesSecondSemester(adapter.getSelectedCourseSecondSemester());
                    ChdtuAdapter adapterFinal = new ChdtuAdapter(selectiveCoursesFinal, getSupportFragmentManager(), null, false);
                    recyclerView.setAdapter(adapterFinal);
                    adapterFinal.disableCheckBoxes();

                    confirmBtn.setOnClickListener((viewConfirm) -> {
                        ConfirmedSelectiveCourses confirmedSelectiveCourses = new ConfirmedSelectiveCourses();
                        confirmedSelectiveCourses.setSelectiveCourses(selectiveCoursesFinal.getSelectiveCoursesIds());
                        ExistingId existingId = new ExistingId(App.getInstance().getCurrentStudent().getDegrees()[0].getId());
                        confirmedSelectiveCourses.setStudentDegreeId(existingId);

                        Log.d("Test", new Gson().toJson(confirmedSelectiveCourses));

                        App.getInstance().getClient().createRequest(SelectiveCourseRequests.class).confirmedSelectiveCourses(
                                App.getInstance().getJwt().getToken(), confirmedSelectiveCourses).enqueue(new Callback<StudentDegreeSelectiveCoursesIds>() {
                            @Override
                            public void onResponse(Call<StudentDegreeSelectiveCoursesIds> call, Response<StudentDegreeSelectiveCoursesIds> response) {
                                try{
                                    Log.d("Test", ""+response.errorBody().string());
                                }catch (IOException e){
                                    e.printStackTrace();
                                }

                                if(response.isSuccessful()){
                                    Log.d("Test", new Gson().toJson(response.body()));
                                    boolean confirmed = true;

                                    for(int i = 0; i < confirmedSelectiveCourses.getSelectiveCourses().size(); i++) {
                                        if(!confirmedSelectiveCourses.getSelectiveCourses().contains(response.body().getSelectiveCourses().get(i).getId())) {
                                            confirmed = false;
                                        }
                                    }

                                    if(confirmed){
                                        Intent intent = new Intent(SelectiveCoursesActivity.this, SelectiveCoursesConfirmed.class);
                                        intent.putExtra("courses", new Gson().toJson(selectiveCoursesFinal));
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<StudentDegreeSelectiveCoursesIds> call, Throwable t) {
                                t.printStackTrace();
                                error("Помилка під час підключення до серверу, спробуйте пізніше.");
                            }
                        });

                    });

                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Оберіть дисципліни", Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SelectiveCoursesActivity.this, MainMenuDrawerActivity.class);
        startActivity(intent);
        finish();
    }

    public void error(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_selectivecourse_info, viewGroup, false);
        ((TextView) dialogView.findViewById(R.id.selectiveCourseName)).setText("Помилка");
        ((TextView) dialogView.findViewById(R.id.selectiveCourseDescription)).setText(text);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        ((Button) dialogView.findViewById(R.id.buttonOk)).setOnClickListener((viewOk) -> {
            alertDialog.dismiss();
        });
    }
 */
}

