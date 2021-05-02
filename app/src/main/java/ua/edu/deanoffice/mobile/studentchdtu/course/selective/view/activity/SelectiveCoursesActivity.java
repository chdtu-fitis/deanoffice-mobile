package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.BaseDrawerActivity;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.DeadLineTimer;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.SelectedCoursesCounter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCoursesSelectionTimeParameters;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCoursesStudentDegree;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.CourseSelectionPeriod;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.service.SelectiveCourseRequests;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.InformationFragment;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SelectiveCoursesConfirmedFragment;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SelectiveCoursesFragment;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment.SelectiveCoursesSecondRoundFragment;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;

public class SelectiveCoursesActivity extends BaseDrawerActivity {

    private View containerHeaders;
    private SelectedCoursesCounter selectedCoursesCounter;
    private SelectiveCourses selectedCourses, availableCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_selective_courses, mainContentBlock, false);
        mainContentBlock.addView(contentView, 1);
        containerHeaders = findViewById(R.id.containerHeaders);
        containerHeaders.setVisibility(View.GONE);

        getSupportActionBar().setTitle(getRString(R.string.action_bar_title_selective_courses));

        loadStudentSelectedCourses();
    }

    private void loadStudentSelectedCourses() {
        showLoadingProgress();

        String jwtToken = App.getInstance().getJwt().getToken();
        int degreesId = App.getInstance().getCurrentStudent().getDegrees()[0].getId();
        App.getInstance().getClient()
                .createRequest(SelectiveCourseRequests.class)
                .studentDegree(jwtToken, degreesId)
                .enqueue(new Callback<SelectiveCoursesStudentDegree>() {
                    @Override
                    public void onResponse(@NonNull Call<SelectiveCoursesStudentDegree> call, @NonNull Response<SelectiveCoursesStudentDegree> response) {
                        hideLoadingProgress();

                        if (response.isSuccessful()) {
                            SelectiveCoursesStudentDegree selectiveCoursesStudentDegree = response.body();
                            List<SelectiveCourse> selectiveCourseList = selectiveCoursesStudentDegree.getSelectiveCourses();
                            if (selectiveCourseList != null) {
                                List<SelectiveCourse> selectiveCoursesFirst = new ArrayList<>();
                                List<SelectiveCourse> selectiveCoursesSecond = new ArrayList<>();

                                for (SelectiveCourse course : selectiveCourseList) {
                                    course.setSelected(true);
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

                                selectedCourses = selectiveCourses;
                            }
                        }
                        loadAvailableSelectiveCourses();
                    }

                    @Override
                    public void onFailure(@NonNull Call<SelectiveCoursesStudentDegree> call, @NonNull Throwable t) {
                        hideLoadingProgress();
                        showError(getRString(R.string.error_connection_failed));
                        loadAvailableSelectiveCourses();
                    }
                });
    }

    private void loadAvailableSelectiveCourses() {
        showLoadingProgress();

        String jwtToken = App.getInstance().getJwt().getToken();
        int degreesId = App.getInstance().getCurrentStudent().getDegrees()[0].getId();
        App.getInstance().getClient()
                .createRequest(SelectiveCourseRequests.class)
                .requestSelectiveCourses(jwtToken, degreesId, true)
                .enqueue(new Callback<SelectiveCourses>() {
                    @Override
                    public void onResponse(@NonNull Call<SelectiveCourses> call, @NonNull Response<SelectiveCourses> response) {
                        hideLoadingProgress();
                        if (response.isSuccessful()) {
                            availableCourses = response.body();
                        }
                        selectAndShowFragment();
                    }

                    @Override
                    public void onFailure(@NonNull Call<SelectiveCourses> call, @NonNull Throwable t) {
                        hideLoadingProgress();
                        selectAndShowFragment();
                    }
                });
    }

    //One point to enter to fragment
    private void selectAndShowFragment() {
        CourseSelectionPeriod period;
        SelectiveCoursesSelectionTimeParameters timeParams;

        period = availableCourses
                .getSelectiveCoursesSelectionTimeParameters()
                .getCourseSelectionPeriod();
        timeParams = selectedCourses.getSelectiveCoursesSelectionTimeParameters();

        ActiveState activeState;
        long timeLeftUntilCurrentRoundEnd = timeParams.getTimeLeftUntilCurrentRoundEnd();
        boolean availableListIsNull = availableCourses == null;
        boolean selectedListIsNull = selectedCourses == null;
        switch (period) {
            case BEFORE_FIRST_ROUND:
                activeState = new BeforeFirstRound(this, timeLeftUntilCurrentRoundEnd);
                break;
            case FIRST_ROUND:
                //Init and show Headers
                headerInit(timeParams);
                activeState = new FirstRound(availableListIsNull, selectedListIsNull);
                break;
            case BETWEEN_FIRST_AND_SECOND_ROUND:
                activeState = new BetweenFirstAndSecondRound(this, timeLeftUntilCurrentRoundEnd, selectedListIsNull);
                break;
            case SECOND_ROUND:
                //Init and show Headers
                headerInit(timeParams);
                activeState = new SecondRound(availableListIsNull, selectedListIsNull);
                break;
            case AFTER_SECOND_ROUND:
            default:
                activeState = new AfterSecondRound(selectedListIsNull);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null && activeState != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, activeState.getFragment())
                    .commit();
        }
    }

    /*
    *   StateMachine Classes
    */

    abstract static class ActiveState {
        protected long time;
        protected final boolean availableListIsNull, selectedListIsNull;
        protected final Context context;

        public ActiveState(Context context, long time, boolean availableListIsNull, boolean selectedListIsNull) {
            this.context = context;
            this.time = time;
            this.availableListIsNull = availableListIsNull;
            this.selectedListIsNull = selectedListIsNull;
        }

        public abstract Fragment getFragment();
    }

    class BeforeFirstRound extends ActiveState {
        public BeforeFirstRound(Context context, long time) {
            super(context, time, true, true);
        }

        @Override
        public Fragment getFragment() {
            return getInformationFragment();
        }

        private InformationFragment getInformationFragment() {
            DeadLineTimer deadLineTimer = new DeadLineTimer(context);
            String timeBeforeNextRoundString = "\n" + getRString(R.string.info_left_before_next_round);

            timeBeforeNextRoundString = timeBeforeNextRoundString.replace("{left_time}", deadLineTimer.deadLine(time));
            String infoMessage = getRString(R.string.info_before_first_round) + timeBeforeNextRoundString;

            return new InformationFragment(infoMessage);
        }
    }

    class FirstRound extends ActiveState {
        public FirstRound(boolean availableListIsNull, boolean selectedListIsNull) {
            super(null, 0, availableListIsNull, selectedListIsNull);
        }

        @Override
        public Fragment getFragment() {
            Fragment fragment;
            if (selectedListIsNull) {
                if (availableListIsNull) {
                    String infoMessage = getRString(R.string.info_failed_load_selective_courses);
                    fragment = new InformationFragment(infoMessage);
                } else {
                    fragment = new SelectiveCoursesFragment(); //TODO: add params available selective courses (availableCourses)
                }
            } else {
                fragment = new SelectiveCoursesConfirmedFragment(selectedCourses);
            }
            return fragment;
        }
    }

    class BetweenFirstAndSecondRound extends ActiveState {
        public BetweenFirstAndSecondRound(Context context, long time, boolean selectedListIsNull) {
            super(context, time, true, selectedListIsNull);
        }

        @Override
        public Fragment getFragment() {
            Fragment fragment;
            if (selectedListIsNull) {
                fragment = getInformationFragment();
            } else {
                fragment = new SelectiveCoursesConfirmedFragment(selectedCourses);
            }
            return fragment;
        }

        private InformationFragment getInformationFragment() {
            DeadLineTimer deadLineTimer = new DeadLineTimer(context);

            String timeBeforeNextRoundString = "\n" + getRString(R.string.info_left_before_next_round);
            timeBeforeNextRoundString = timeBeforeNextRoundString.replace("{left_time}", deadLineTimer.deadLine(time));

            String infoMessage = getRString(R.string.info_between_first_and_second_round) + timeBeforeNextRoundString;
            return new InformationFragment(infoMessage);
        }
    }

    class SecondRound extends ActiveState {
        public SecondRound(boolean availableListIsNull, boolean selectedListIsNull) {
            super(null, 0, availableListIsNull, selectedListIsNull);
        }

        @Override
        public Fragment getFragment() {
            Fragment fragment;
            if (selectedListIsNull) {
                if (availableListIsNull) {
                    fragment = getInformationFragment();
                } else {
                    fragment = new SelectiveCoursesSecondRoundFragment(availableCourses);
                }
            } else {
                if (existDisqualifiedCourse(selectedCourses)) {
                    if (availableListIsNull) {
                        fragment = getInformationFragment();
                    } else {
                        fragment = new SelectiveCoursesSecondRoundFragment(availableCourses, selectedCourses);
                    }
                } else {
                    fragment = new SelectiveCoursesConfirmedFragment(selectedCourses);
                }
            }
            return fragment;
        }

        private boolean existDisqualifiedCourse(SelectiveCourses selectedCourses) {
            boolean existDisqualifiedCourse = false;
            List<SelectiveCourse> firstSemesterCoursesList = selectedCourses.getSelectiveCoursesFirstSemester();
            List<SelectiveCourse> secondSemesterCoursesList = selectedCourses.getSelectiveCoursesSecondSemester();

            for (SelectiveCourse course : firstSemesterCoursesList) {
                if (!course.isAvailable()) {
                    existDisqualifiedCourse = true;
                    break;
                }
            }
            for (SelectiveCourse course : secondSemesterCoursesList) {
                if (existDisqualifiedCourse) {
                    break;
                }
                if (!course.isAvailable()) {
                    existDisqualifiedCourse = true;
                    break;
                }
            }
            return existDisqualifiedCourse;
        }

        private InformationFragment getInformationFragment() {
            String infoMessage = getRString(R.string.info_failed_load_selective_courses);
            return new InformationFragment(infoMessage);
        }
    }

    class AfterSecondRound extends ActiveState {
        public AfterSecondRound(boolean selectedListIsNull) {
            super(null,0, true, selectedListIsNull);
        }

        @Override
        public Fragment getFragment() {
            Fragment fragment;
            if (selectedListIsNull) {
                fragment = getInformationFragment();
            } else {
                fragment = new SelectiveCoursesConfirmedFragment(selectedCourses);
            }
            return fragment;
        }

        private InformationFragment getInformationFragment() {
            String infoMessage = getRString(R.string.info_after_second_round);
            return new InformationFragment(infoMessage);
        }
    }

    /*
     *   StateMachine Classes End
     */

    private void headerInit(SelectiveCoursesSelectionTimeParameters timeParams) {
        TextView studyYearsTV = findViewById(R.id.textStudyYears);
        TextView leftTimeToEndRoundTV = findViewById(R.id.textLeftTimeToEndRound);
        TextView selectedCoursesCounterTV = findViewById(R.id.textSelectedCoursesCounter);

        int studyYear = timeParams.getStudyYear();
        String studyYearsString = getRString(R.string.header_study_years);
        studyYearsString = studyYearsString.replace("{study_year_begin}", String.valueOf(studyYear));
        studyYearsString = studyYearsString.replace("{study_year_end}", String.valueOf(studyYear + 1));

        studyYearsTV.setText(studyYearsString);

        long leftTimeToEndRound = timeParams.getTimeLeftUntilCurrentRoundEnd();
        DeadLineTimer deadLineTimer = new DeadLineTimer(this);
        String leftTimeToEndRoundString = getRString(R.string.header_left_time_to_end_round);
        leftTimeToEndRoundString = leftTimeToEndRoundString.replace("{left_time}", deadLineTimer.deadLine(leftTimeToEndRound));

        leftTimeToEndRoundTV.setText(leftTimeToEndRoundString);

        selectedCoursesCounter = new SelectedCoursesCounter(selectedCoursesCounterTV, 2, 3); //TODO: replace constant magic number to value from backend
        selectedCoursesCounter.init();
        containerHeaders.setVisibility(View.VISIBLE);
    }
}

