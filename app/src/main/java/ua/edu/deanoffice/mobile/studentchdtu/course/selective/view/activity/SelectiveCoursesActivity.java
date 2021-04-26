package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.BaseDrawerActivity;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCoursesSelectionTimeParameters;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCoursesStudentDegree;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.CourseSelectionPeriod;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.service.SelectiveCourseRequests;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.InformationFragment;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SelectiveCoursesConfirmedFragment;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SelectiveCoursesFragment;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;

public class SelectiveCoursesActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_selective_courses, mainContentBlock, false);
        mainContentBlock.addView(contentView, 1);

        getSupportActionBar().setTitle(getRString(R.string.action_bar_title_selective_courses));

        loadStudentSelectedCourses();
    }

    private void loadStudentSelectedCourses() {
        showLoadingDialog();

        String jwtToken = App.getInstance().getJwt().getToken();
        int degreesId = App.getInstance().getCurrentStudent().getDegrees()[0].getId();
        App.getInstance().getClient()
                .createRequest(SelectiveCourseRequests.class)
                .studentDegree(jwtToken, degreesId)
                .enqueue(new Callback<SelectiveCoursesStudentDegree>() {
                    @Override
                    public void onResponse(@NonNull Call<SelectiveCoursesStudentDegree> call, @NonNull Response<SelectiveCoursesStudentDegree> response) {
                        hideLoadingDialog();

                        if (response.isSuccessful()) {
                            SelectiveCoursesStudentDegree selectiveCoursesStudentDegree = response.body();
                            List<SelectiveCourse> selectiveCourseList = selectiveCoursesStudentDegree.getSelectiveCourses();
                            if (selectiveCourseList != null) {
                                List<SelectiveCourse> selectiveCoursesFirst = new ArrayList<>();
                                List<SelectiveCourse> selectiveCoursesSecond = new ArrayList<>();

                                for (SelectiveCourse course : selectiveCourseList) {
                                    course.selected = true;
                                    if (course.getCourse().getSemester() % 2 != 0) {
                                        selectiveCoursesFirst.add(course);
                                    } else {
                                        selectiveCoursesSecond.add(course);
                                    }
                                }

                                SelectiveCourses selectiveCourses = new SelectiveCourses();
                                selectiveCourses.setSelectiveCoursesFirstSemester(selectiveCoursesFirst);
                                selectiveCourses.setSelectiveCoursesSecondSemester(selectiveCoursesSecond);
                                SelectiveCoursesSelectionTimeParameters timeParameters = selectiveCoursesStudentDegree.getSelectiveCoursesSelectionTimeParameters();
                                selectiveCourses.setSelectiveCoursesSelectionTimeParameters(timeParameters);

                                selectAndShowFragment(selectiveCourses, true);
                            } else {
                                loadAvailableSelectiveCourses();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SelectiveCoursesStudentDegree> call, @NonNull Throwable t) {
                        hideLoadingDialog();
                        error("Помилка під час підключення до серверу, спробуйте пізніше.");
                    }
                });
    }

    private void loadAvailableSelectiveCourses() {
        showLoadingDialog();

        String jwtToken = App.getInstance().getJwt().getToken();
        int degreesId = App.getInstance().getCurrentStudent().getDegrees()[0].getId();
        App.getInstance().getClient()
                .createRequest(SelectiveCourseRequests.class)
                .requestSelectiveCourses(jwtToken, degreesId, true)
                .enqueue(new Callback<SelectiveCourses>() {
                    @Override
                    public void onResponse(@NonNull Call<SelectiveCourses> call, @NonNull Response<SelectiveCourses> response) {
                        hideLoadingDialog();
                        if (response.isSuccessful()) {
                            selectAndShowFragment(response.body(), false);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SelectiveCourses> call, @NonNull Throwable t) {
                        hideLoadingDialog();
                    }
                });
    }

    //One point to enter to fragment
    private void selectAndShowFragment(SelectiveCourses selectiveCourses, boolean didStudentChoseCourses) {
        CourseSelectionPeriod period = selectiveCourses
                .getSelectiveCoursesSelectionTimeParameters()
                .getCourseSelectionPeriod();

        //TODO: Need to get selectiveCourses for first and second semester and send to SelectiveCoursesFragments (First Round or Second Round);

        Fragment fragment;
        if (didStudentChoseCourses) {
            fragment = new SelectiveCoursesConfirmedFragment(selectiveCourses);
        } else {
            String timeBeforeNextRound = "\n" + getRString(R.string.info_left_before_next_round);
            timeBeforeNextRound = timeBeforeNextRound.replace("{left_time}", "ще трохи"); //TODO: Need Class Handler for Time (from millisecond to string "left before")

            String infoMessage;
            switch (period) {
                case BEFORE_FIRST_ROUND:
                    infoMessage = getRString(R.string.info_before_first_round) + timeBeforeNextRound;
                    fragment = new InformationFragment(infoMessage);
                    break;
                case FIRST_ROUND:
                    fragment = new SelectiveCoursesFragment();
                    break;
                case BETWEEN_FIRST_AND_SECOND_ROUND:
                    infoMessage = getRString(R.string.info_between_first_and_second_round) + timeBeforeNextRound;
                    fragment = new InformationFragment(infoMessage);
                    break;
                case SECOND_ROUND:
                    fragment = new SelectiveCoursesFragment(); //TODO: Need SelectiveCoursesFragmentSecondRound
                    break;
                case AFTER_SECOND_ROUND:
                default:
                    infoMessage = getRString(R.string.info_after_second_round) + timeBeforeNextRound;
                    fragment = new InformationFragment(infoMessage);
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
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
}

