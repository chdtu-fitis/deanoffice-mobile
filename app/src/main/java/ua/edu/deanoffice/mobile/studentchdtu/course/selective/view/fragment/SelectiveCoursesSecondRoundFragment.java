package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ConfirmedSelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ExistingId;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCoursesSelectionTimeParameters;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.StudentDegreeSelectiveCoursesIds;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.service.SelectiveCourseRequests;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.SelectiveCoursesAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity.SelectiveCoursesActivity;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;

public class SelectiveCoursesSecondRoundFragment extends BaseSelectiveCoursesFragment {
    private TextView textSelectiveCoursesCounter;
    private final SelectiveCourses availableSelectiveCourses, selectedSelectiveCourses;

    public SelectiveCoursesSecondRoundFragment() {
        this(null, null);
    }

    public SelectiveCoursesSecondRoundFragment(SelectiveCourses availableSelectiveCourses,
                                               SelectiveCourses selectedSelectiveCourses) {
        this.availableSelectiveCourses = availableSelectiveCourses;
        this.selectedSelectiveCourses = selectedSelectiveCourses;
    }

    public SelectiveCoursesSecondRoundFragment(SelectiveCourses availableCourses) {
        this(availableCourses, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selective_courses_second_round, container, false);
        textSelectiveCoursesCounter = view.findViewById(R.id.text_body);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        fillSelectiveCoursesList();
    }

/*
    public void loadSelectedSelectiveCourses() {
        App.getInstance()
                .getClient()
                .createRequest(SelectiveCourseRequests.class)
                .studentDegree(
                        App.getInstance().getJwt().getToken(),
                        App.getInstance().getCurrentStudent().getDegrees()[0].getId())
                .enqueue(new Callback<SelectiveCoursesStudentDegree>() {
                    @Override
                    public void onResponse(@NonNull Call<SelectiveCoursesStudentDegree> call, @NonNull Response<SelectiveCoursesStudentDegree> response) {
                        hideLoadingProgress();

                        if (response.isSuccessful()) {
                            SelectiveCoursesStudentDegree selectiveCoursesStudentDegree = response.body();
                            List<SelectiveCourse> selectiveCourseList = selectiveCoursesStudentDegree.getSelectiveCourses();
                            if (selectiveCourseList != null) {
                                boolean existOnceDisqualifiedCourse = false;
                                List<SelectiveCourse> selectiveCoursesFirst = new ArrayList<>();
                                List<SelectiveCourse> selectiveCoursesSecond = new ArrayList<>();

                                for (SelectiveCourse course : selectiveCourseList) {
                                    if (!course.isAvailable() && !existOnceDisqualifiedCourse) {
                                        existOnceDisqualifiedCourse = true;
                                    }
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

                                onLoadSelectedSelectiveCourses(selectiveCourses, existOnceDisqualifiedCourse);
                            } else {
                                onLoadSelectedSelectiveCourses(null, true);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SelectiveCoursesStudentDegree> call, @NonNull Throwable t) {
                        hideLoadingProgress();
                    }
                });
    }

    private void onLoadSelectedSelectiveCourses(SelectiveCourses selectiveCourses, boolean existOnceDisqualifiedCourse) {
        if (!existOnceDisqualifiedCourse) {
            Fragment fragment = new SelectiveCoursesConfirmedFragment(selectiveCourses);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            if (fragmentManager != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        } else {
            selectedSelectiveCourses = selectiveCourses;
            loadAvailableSelectiveCourses();
        }
    }

    public void loadAvailableSelectiveCourses() {
        showLoadingProgress();

        App.getInstance()
                .getClient()
                .createRequest(SelectiveCourseRequests.class)
                .requestSelectiveCourses(
                        App.getInstance().getJwt().getToken(),
                        App.getInstance().getCurrentStudent().getDegrees()[0].getId(),
                        true)
                .enqueue(new Callback<SelectiveCourses>() {
                    @Override
                    public void onResponse(@NonNull Call<SelectiveCourses> call, @NonNull Response<SelectiveCourses> response) {
                        hideLoadingProgress();

                        if (response.isSuccessful()) {
                            if (getActivity() != null)
                                getActivity().runOnUiThread(() -> {
                                    fillSelectiveCoursesList(response.body());
                                });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SelectiveCourses> call, @NonNull Throwable t) {
                        hideLoadingProgress();
                    }
                });
    }
*/

    private void fillSelectiveCoursesList() {
        if (getView() == null) return;
        SelectiveCourses showingSelectiveCourses = null;

        if (selectedSelectiveCourses == null) {
            showingSelectiveCourses = availableSelectiveCourses;
        } else if (availableSelectiveCourses != null) {
            showingSelectiveCourses = uniteAvailableAndSelectedCourses(availableSelectiveCourses, selectedSelectiveCourses);
        }
        if (showingSelectiveCourses == null) {
            return;
        }

        RecyclerView recyclerView = getView().findViewById(R.id.listview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        boolean isMagister = App.getInstance().getCurrentStudent().getDegrees()[0].getSpecialization().getDegree().getId() == 3;

        Button clearButton = getView().findViewById(R.id.clear_selectivecourses);
        Button confirmButton = getView().findViewById(R.id.confirm_selectivecourses);

        SelectiveCoursesAdapter adapter = new SelectiveCoursesAdapter(showingSelectiveCourses, getFragmentManager(), textSelectiveCoursesCounter, true, isMagister);
        adapter.setSelectListener(new SelectiveCoursesAdapter.SelectListener() {
            @Override
            public void onOneSelected() {
                enableButton(clearButton);
            }

            @Override
            public void onAllSelected() {
                enableButton(confirmButton);
            }

            @Override
            public void onLastDeselected() {
                disableButton(clearButton);
            }

            @Override
            public void onNotAllSelected() {
                disableButton(confirmButton);
            }
        });
        recyclerView.setAdapter(adapter);

        clearButton.setOnClickListener((button) -> adapter.clearSelected());
        confirmButton.setOnClickListener((button) -> {
            if (adapter.getSelectedCourseFirstSemester().size() == adapter.getMaxCoursesFirstSemester() &&
                    adapter.getSelectedCourseSecondSemester().size() == adapter.getMaxCoursesSecondSemester()) {

                textSelectiveCoursesCounter.setText(getRString(R.string.info_confirm_sel_courses));
                confirmButton.setText(getRString(R.string.button_confirm));
                clearButton.setText(getRString(R.string.button_cancel));

                clearButton.setOnClickListener((v) -> {
                    Intent intent = new Intent(getContext(), SelectiveCoursesActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                });

                SelectiveCourses selectiveCoursesFinal = new SelectiveCourses();
                selectiveCoursesFinal.setSelectiveCoursesFirstSemester(adapter.getSelectedCourseFirstSemester());
                selectiveCoursesFinal.setSelectiveCoursesSecondSemester(adapter.getSelectedCourseSecondSemester());

                SelectiveCoursesAdapter adapterFinal = new SelectiveCoursesAdapter(selectiveCoursesFinal, getFragmentManager());
                recyclerView.setAdapter(adapterFinal);
                adapterFinal.disableCheckBoxes();

                confirmButton.setOnClickListener((viewConfirm) -> saveUserChose(selectiveCoursesFinal));
            } else {
                Snackbar.make(button.findViewById(android.R.id.content), getRString(R.string.worn_select_courses), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }

    private SelectiveCourses uniteAvailableAndSelectedCourses(SelectiveCourses availableSelectiveCourses,
                                                              SelectiveCourses selectedSelectiveCourses) {
        SelectiveCourses resultSelectiveCourses = new SelectiveCourses();
        SelectiveCoursesSelectionTimeParameters timeParameters = availableSelectiveCourses.getSelectiveCoursesSelectionTimeParameters();

        resultSelectiveCourses.setSelectiveCoursesSelectionTimeParameters(timeParameters);

        if (selectedSelectiveCourses != null) {
            List<SelectiveCourse> selectiveCoursesFirstSemester;
            List<SelectiveCourse> selectiveCoursesSecondSemester;
            selectiveCoursesFirstSemester = unitSemesterAvailableAndSelectedCourses(
                    availableSelectiveCourses.getSelectiveCoursesFirstSemester(),
                    selectedSelectiveCourses.getSelectiveCoursesFirstSemester()
            );
            selectiveCoursesSecondSemester = unitSemesterAvailableAndSelectedCourses(
                    availableSelectiveCourses.getSelectiveCoursesSecondSemester(),
                    selectedSelectiveCourses.getSelectiveCoursesSecondSemester()
            );
            resultSelectiveCourses.setSelectiveCoursesFirstSemester(selectiveCoursesFirstSemester);
            resultSelectiveCourses.setSelectiveCoursesSecondSemester(selectiveCoursesSecondSemester);
        } else {
            resultSelectiveCourses = availableSelectiveCourses;
        }

        return resultSelectiveCourses;
    }

    private List<SelectiveCourse> unitSemesterAvailableAndSelectedCourses(List<SelectiveCourse> availableSelectiveCourseList,
                                                                          List<SelectiveCourse> selectedSelectiveCourseList) {
        List<SelectiveCourse> resultCoursesList = new ArrayList<>();
        resultCoursesList.addAll(availableSelectiveCourseList);
        for (SelectiveCourse selectedCourse : selectedSelectiveCourseList) {
            boolean elementIncluded = false;
            for (SelectiveCourse availableCourse : availableSelectiveCourseList) {
                if (selectedCourse.getId() == availableCourse.getId()) {
                    availableCourse.setSelected(true);
                    elementIncluded = true;
                    break;
                }
            }
            if (!elementIncluded) {
                resultCoursesList.add(selectedCourse);
            }
        }

        return resultCoursesList;
    }

    private void saveUserChose(SelectiveCourses selectiveCourses) {
        showLoadingProgress();

        ConfirmedSelectiveCourses confirmedSelectiveCourses = new ConfirmedSelectiveCourses();

        if (selectedSelectiveCourses != null) {
            List<Integer> newSelectedCourses = new ArrayList<>();
            for (Integer courseId : selectiveCourses.getSelectiveCoursesIds()) {
                boolean elementIncluded = false;
                for (Integer existingCourseId : selectedSelectiveCourses.getSelectiveCoursesIds()) {
                    if (courseId.equals(existingCourseId)) {
                        elementIncluded = true;
                        break;
                    }
                }
                if (!elementIncluded) {
                    newSelectedCourses.add(courseId);
                }
            }
            confirmedSelectiveCourses.setSelectiveCourses(newSelectedCourses);
        } else {
            confirmedSelectiveCourses.setSelectiveCourses(selectiveCourses.getSelectiveCoursesIds());
        }

        int degreesId = App.getInstance().getCurrentStudent().getDegrees()[0].getId();
        ExistingId existingId = new ExistingId(degreesId);
        confirmedSelectiveCourses.setStudentDegree(existingId);

        App.getInstance().getClient().createRequest(SelectiveCourseRequests.class).confirmedSelectiveCourses(
                App.getInstance().getJwt().getToken(), confirmedSelectiveCourses).enqueue(new Callback<StudentDegreeSelectiveCoursesIds>() {
            @Override
            public void onResponse(@NonNull Call<StudentDegreeSelectiveCoursesIds> call, @NonNull Response<StudentDegreeSelectiveCoursesIds> response) {
                if (response.isSuccessful()) {
                    boolean confirmed = true;

                    for (int i = 0; i < confirmedSelectiveCourses.getSelectiveCourses().size(); i++) {
                        if (!confirmedSelectiveCourses.getSelectiveCourses().contains(response.body().getSelectiveCourses().get(i).getId())) {
                            confirmed = false;
                        }
                    }
                    if (confirmed) {
                        if (getFragmentManager() != null) {
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelectiveCoursesConfirmedFragment(selectiveCourses)).commit();
                        }
                    }
                } else {
                    try {
                        showError(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                hideLoadingProgress();
            }

            @Override
            public void onFailure(@NonNull Call<StudentDegreeSelectiveCoursesIds> call, @NonNull Throwable t) {
                hideLoadingProgress();
                t.printStackTrace();
                showError(getRString(R.string.error_connection_failed));
            }
        });
    }
}