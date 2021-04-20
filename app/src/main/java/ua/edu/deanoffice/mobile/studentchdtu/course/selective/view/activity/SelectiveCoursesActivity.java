package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.BaseDrawerActivity;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SelectiveCoursesSecondRoundFragment;

public class SelectiveCoursesActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_selective_courses, mainContentBlock, false);
        mainContentBlock.addView(contentView, 1);

        getSupportActionBar().setTitle(getRString(R.string.action_bar_title_selective_courses));

        //Init views

        //Get selective courses
        //Get data time, it is  second round or first
        //Show selective courses to fragment

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SelectiveCoursesSecondRoundFragment()).commit();

       // showLoadingProgress();

//        App.getInstance()
//                .getClient()
//                .createRequest(SelectiveCourseRequests.class)
//                .requestSelectiveCourses(
//                        App.getInstance().getJwt().getToken(),
//                        App.getInstance().getCurrentStudent().getDegrees()[0].getId()
//                ).enqueue(new Callback<SelectiveCourses>() {
//            @Override
//            public void onResponse(@NotNull Call<SelectiveCourses> call, @NotNull Response<SelectiveCourses> response) {
//                hideLoadingProgress();
//                if (response.isSuccessful()) {
//                    // SelectiveCoursesActivity.this.onResponse(response.body());
//                }
//                //Handling error response codes
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<SelectiveCourses> call, @NotNull Throwable t) {
//                hideLoadingProgress();
//                //Handling errors
//            }
//        });
    }

//    private void onResponse(SelectiveCourses selectiveCourses) {
//        RecyclerView recyclerView = findViewById(R.id.listview);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        ChdtuAdapter adapter = new ChdtuAdapter(selectiveCourses, getSupportFragmentManager(), selectiveCoursesCounter, true);
//        recyclerView.setAdapter(adapter);
//
//        Button clearBtn = findViewById(R.id.clear_selectivecourses);
//        clearBtn.setOnClickListener((view) -> {
//            adapter.clearSelected();
//        });
//
//        Button confirmBtn = findViewById(R.id.confirm_selectivecourses);
//        confirmBtn.setOnClickListener((view) -> {
//            onClickConfirmButton();
//        });
//    }
//
//    private void onClickConfirmButton(){
//        if (adapter.getSelectedCourseFirstSemester().size() == 3 && adapter.getSelectedCourseSecondSemester().size() == 2) {
//            selectiveCoursesCounter.setText(getRString(R.string.info_confirm_sel_courses));
//            confirmBtn.setText(getRString(R.string.button_confirm));
//            clearBtn.setText(getRString(R.string.button_cancel));
//
//            clearBtn.setOnClickListener((viewClear) -> {
//                Intent intent = new Intent(SelectiveCoursesActivity.this, SelectiveCoursesActivity.class);
//                startActivity(intent);
//                finish();
//            });
//
//            SelectiveCourses selectiveCoursesFinal = new SelectiveCourses();
//            selectiveCoursesFinal.setSelectiveCoursesFirstSemester(adapter.getSelectedCourseFirstSemester());
//            selectiveCoursesFinal.setSelectiveCoursesSecondSemester(adapter.getSelectedCourseSecondSemester());
//            ChdtuAdapter adapterFinal = new ChdtuAdapter(selectiveCoursesFinal, getSupportFragmentManager(), null, false);
//            recyclerView.setAdapter(adapterFinal);
//            adapterFinal.disableCheckBoxes();
//
//            confirmBtn.setOnClickListener((viewConfirm) -> {
//                ConfirmedSelectiveCourses confirmedSelectiveCourses = new ConfirmedSelectiveCourses();
//                confirmedSelectiveCourses.setSelectiveCourses(selectiveCoursesFinal.getSelectiveCoursesIds());
//                ExistingId existingId = new ExistingId(App.getInstance().getCurrentStudent().getDegrees()[0].getId());
//                confirmedSelectiveCourses.setStudentDegreeId(existingId);
//
//                App.getInstance()
//                        .getClient()
//                        .createRequest(SelectiveCourseRequests.class)
//                        .confirmedSelectiveCourses(
//                                App.getInstance().getJwt().getToken(),
//                                confirmedSelectiveCourses)
//                        .enqueue(new Callback<StudentDegreeSelectiveCoursesIds>() {
//                            @Override
//                            public void onResponse(@NotNull Call<StudentDegreeSelectiveCoursesIds> call, @NotNull Response<StudentDegreeSelectiveCoursesIds> response) {
//                                if (response.isSuccessful()) {
//                                    boolean confirmed = true;
//
//                                    for (int i = 0; i < confirmedSelectiveCourses.getSelectiveCourses().size(); i++) {
//                                        if (!confirmedSelectiveCourses.getSelectiveCourses().contains(response.body().getSelectiveCourses().get(i).getId())) {
//                                            confirmed = false;
//                                        }
//                                    }
//                                    if (confirmed) {
//                                        Intent intent = new Intent(SelectiveCoursesActivity.this, SelectiveCoursesConfirmed.class);
//                                        intent.putExtra("courses", new Gson().toJson(selectiveCoursesFinal));
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(@NotNull Call<StudentDegreeSelectiveCoursesIds> call, @NotNull Throwable t) {
//                                t.printStackTrace();
//                                showError(getRString(R.string.error_connection_failed));
//                            }
//                        });
//            });
//        } else {
//            Snackbar.make(findViewById(android.R.id.content), getRString(R.string.info_sel_courses), Snackbar.LENGTH_LONG)
//                    .setAction("No action", null).show();
//        }
//    }

    private void showError(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_selectivecourse_info, viewGroup, false);

        TextView titleText = dialogView.findViewById(R.id.selectiveCourseName);
        TextView bodyText = dialogView.findViewById(R.id.selectiveCourseDescription);

        titleText.setText(getRString(R.string.error_msg_title));
        bodyText.setText(msg);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dialogView.findViewById(R.id.buttonOk).setOnClickListener((viewOk) -> {
            alertDialog.dismiss();
        });
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

