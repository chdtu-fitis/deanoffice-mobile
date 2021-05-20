package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.BaseDrawerActivity;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.SelectedCoursesCounter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ConfirmedSelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ExistingId;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourse;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.StudentDegreeSelectiveCoursesIds;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.enums.Semester;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.service.SelectiveCourseRequests;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.SelectiveCoursesAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity.SelectiveCoursesActivity;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;

public abstract class BaseSelectiveCoursesFragment extends Fragment {
    protected SelectedCoursesCounter selectedCoursesCounter;
    protected RecyclerView recyclerView;
    protected SelectiveCoursesAdapter adapterFirstSemester, adapterSecondSemester;
    protected SelectiveCourses showingSelectiveCourses;
    protected Button clearButton, confirmButton;
    protected View toFirstSemesterButton, toSecondSemesterButton;

    public BaseSelectiveCoursesFragment(SelectiveCourses selectiveCourses, SelectedCoursesCounter selectedCoursesCounter) {
        this.showingSelectiveCourses = selectiveCourses;
        this.selectedCoursesCounter = selectedCoursesCounter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selectivecourses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.listview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        clearButton = view.findViewById(R.id.clear_selectivecourses);
        confirmButton = view.findViewById(R.id.confirm_selectivecourses);

        toFirstSemesterButton = view.findViewById(R.id.buttonToFirstSemester);
        toSecondSemesterButton = view.findViewById(R.id.buttonToSecondSemester);

        toFirstSemesterButton.setOnClickListener(v -> selectSemester(Semester.FIRST));
        toSecondSemesterButton.setOnClickListener(v -> selectSemester(Semester.SECOND));

        initAdapters();

        clearButton.setOnClickListener((v) -> {
            if (recyclerView != null) {
                SelectiveCoursesAdapter adapter = (SelectiveCoursesAdapter) recyclerView.getAdapter();
                if (adapter != null) {
                    adapter.clearSelected();
                }
            }
        });
        confirmButton.setOnClickListener(this::onClickConfirmButton);

        selectedCoursesCounter.setSelectListener(new SelectedCoursesCounter.SelectListener() {
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
    }

    /**
     * Create adapters.
     */
    protected void initAdapters() {
        if (showingSelectiveCourses == null) return;

        List<SelectiveCourse> selectiveCourseList;
        selectiveCourseList = showingSelectiveCourses.getSelectiveCoursesFirstSemester();
        adapterFirstSemester = new SelectiveCoursesAdapter(selectiveCourseList, selectedCoursesCounter, Semester.FIRST);

        selectiveCourseList = showingSelectiveCourses.getSelectiveCoursesSecondSemester();
        adapterSecondSemester = new SelectiveCoursesAdapter(selectiveCourseList, selectedCoursesCounter, Semester.SECOND);

        Semester semester = Semester.FIRST;
        if (recyclerView != null) {
            SelectiveCoursesAdapter adapter = (SelectiveCoursesAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                semester = adapter.getSemester();
            }
        }
        selectSemester(semester);
    }

    //Go from confirm mode to normal list
    protected void onClickButtonBackFromConfirmFragment(View button) {
        //Change Headers
        showHeaders(SelectiveCoursesActivity.Headers.SELECTION);

        //Rename Buttons
        confirmButton.setText(getRString(R.string.button_next));
        clearButton.setText(getRString(R.string.button_uncheck_selection));

        List<SelectiveCoursesAdapter> adaptersList = new ArrayList<>();
        adaptersList.add(adapterFirstSemester);
        adaptersList.add(adapterSecondSemester);
        for (SelectiveCoursesAdapter adapter : adaptersList) {
            if (adapter != null) {
                //Show hidden courses in list
                adapter.showAllHiddenCourseFragments();
                //Unblocked Interactive With CheckBox
                adapter.disableCheckBoxes(false);
            }
        }
        //Change ClearButton onClickListener
        clearButton.setOnClickListener((v) -> {
            SelectiveCoursesAdapter adapter = (SelectiveCoursesAdapter) recyclerView.getAdapter();
            adapter.clearSelected();
        });
        //Change ConfirmButton onClickListener
        confirmButton.setOnClickListener(this::onClickConfirmButton);
    }

    //Go to confirm mode from normal list
    protected void onClickConfirmButton(View button) {
        if (selectedCoursesCounter.confirmIsAvailable()) {
            //Change Headers
            showHeaders(SelectiveCoursesActivity.Headers.CONFIRM);

            //Rename Buttons
            confirmButton.setText(getRString(R.string.button_confirm));
            clearButton.setText(getRString(R.string.button_cancel));

            //Change ClearButton onClickListener
            clearButton.setOnClickListener(this::onClickButtonBackFromConfirmFragment);

            List<SelectiveCoursesAdapter> adaptersList = new ArrayList<>();
            adaptersList.add(adapterFirstSemester);
            adaptersList.add(adapterSecondSemester);
            SelectiveCourses selectiveCoursesFinal = new SelectiveCourses();
            for (SelectiveCoursesAdapter adapter : adaptersList) {
                if (adapter != null) {
                    //Hide unchecked and disqualified courses in list
                    adapter.hideAllUncheckedCourseFragments();

                    if (adapter.getSemester() == Semester.FIRST) {
                        selectiveCoursesFinal.setSelectiveCoursesFirstSemester(adapter.getSelectedCourse());
                    } else {
                        selectiveCoursesFinal.setSelectiveCoursesSecondSemester(adapter.getSelectedCourse());
                    }

                    //Blocked Interactive With CheckBox
                    adapter.disableCheckBoxes(true);
                } else {
                    showError(getRString(R.string.error_null_selective_courses_adapter));
                }
            }
            //Change ConfirmButton onClickListener
            confirmButton.setOnClickListener((viewConfirm) -> saveUserChoice(selectiveCoursesFinal));
        } else {
            Snackbar.make(button.findViewById(android.R.id.content), getRString(R.string.worn_select_courses), Snackbar.LENGTH_LONG)
                    .setAction("No action", null).show();
        }
    }

    protected void saveUserChoice(SelectiveCourses selectiveCourses) {
        showLoadingProgress();

        ConfirmedSelectiveCourses confirmedSelectiveCourses = getConfirmedSelectiveCourses(selectiveCourses);

        int degreesId = App.getInstance().getCurrentStudent().getDegrees()[0].getId();
        ExistingId existingId = new ExistingId(degreesId);
        confirmedSelectiveCourses.setStudentDegree(existingId);

        App.getInstance().getClient().createRequest(SelectiveCourseRequests.class)
                .confirmedSelectiveCourses(App.getInstance().getJwt().getToken(), confirmedSelectiveCourses)
                .enqueue(new Callback<StudentDegreeSelectiveCoursesIds>() {
                    @Override
                    public void onResponse(@NonNull Call<StudentDegreeSelectiveCoursesIds> call, @NonNull Response<StudentDegreeSelectiveCoursesIds> response) {
                        if (response.isSuccessful()) {
                            boolean confirmed = true;

                            for (int i = 0; i < confirmedSelectiveCourses.getSelectiveCourses().size(); i++) {
                                if (!confirmedSelectiveCourses.getSelectiveCourses().contains(response.body().getSelectiveCourses().get(i).getId())) {
                                    confirmed = false;
                                    break;
                                }
                            }
                            if (confirmed) {
                                FragmentManager fragmentManager = getFragmentManager();
                                if (fragmentManager != null) {
                                    showHeaders(SelectiveCoursesActivity.Headers.REGISTERED);
                                    Fragment fragment = new SelectiveCoursesConfirmedFragment(selectiveCourses);
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.fragment_container, fragment)
                                            .commit();
                                }
                            }
                        } else {
                            showError(getServerErrorMessage(response));
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

    protected ConfirmedSelectiveCourses getConfirmedSelectiveCourses(SelectiveCourses selectiveCourses) {
        ConfirmedSelectiveCourses confirmedSelectiveCourses = new ConfirmedSelectiveCourses();
        confirmedSelectiveCourses.setSelectiveCourses(selectiveCourses.getSelectiveCoursesIds());
        return confirmedSelectiveCourses;
    }

    protected void selectSemester(Semester semester) {
        switch (semester) {
            case FIRST:
                toSecondSemesterButton.setVisibility(View.VISIBLE);
                toFirstSemesterButton.setVisibility(View.GONE);
                showFirstSemester();
                break;
            case SECOND:
                toFirstSemesterButton.setVisibility(View.VISIBLE);
                toSecondSemesterButton.setVisibility(View.GONE);
                showSecondSemester();
                break;
        }
    }

    protected void showFirstSemester() {
        if (showingSelectiveCourses == null) return;
        if (adapterFirstSemester == null) {
            List<SelectiveCourse> selectiveCourseList = showingSelectiveCourses.getSelectiveCoursesFirstSemester();
            adapterFirstSemester = new SelectiveCoursesAdapter(selectiveCourseList, selectedCoursesCounter, Semester.FIRST);
        }
        recyclerView.setAdapter(adapterFirstSemester);

        if(selectedCoursesCounter != null){
            selectedCoursesCounter.switchSemester(Semester.FIRST);
            adapterFirstSemester.updateItemCountView();
        }
    }

    protected void showSecondSemester() {
        if (showingSelectiveCourses == null) return;
        if (adapterSecondSemester == null) {
            List<SelectiveCourse> selectiveCourseList = showingSelectiveCourses.getSelectiveCoursesSecondSemester();
            adapterSecondSemester = new SelectiveCoursesAdapter(selectiveCourseList, selectedCoursesCounter, Semester.SECOND);
        }
        recyclerView.setAdapter(adapterSecondSemester);

        if(selectedCoursesCounter != null){
            selectedCoursesCounter.switchSemester(Semester.SECOND);
            adapterSecondSemester.updateItemCountView();
        }
    }

    protected void disableButton(View button) {
        button.setAlpha(0.5f);
        button.setEnabled(false);
    }

    protected void enableButton(View button) {
        button.setAlpha(1f);
        button.setEnabled(true);
    }

    protected void showLoadingProgress() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((SelectiveCoursesActivity) activity).showLoadingProgress();
        }
    }

    protected void hideLoadingProgress() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((SelectiveCoursesActivity) activity).hideLoadingProgress();
        }
    }

    protected void showError(String msg) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((SelectiveCoursesActivity) activity).showError(msg);
        }
    }

    protected void showError(String msg, BaseDrawerActivity.ErrorDialogListener action) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((SelectiveCoursesActivity) activity).showError(msg, action);
        }
    }

    protected String getRString(int stringResource) {
        if (getContext() != null) {
            return getContext().getResources().getString(stringResource);
        }
        return "";
    }

    protected void showHeaders(SelectiveCoursesActivity.Headers headers) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((SelectiveCoursesActivity) activity).setUpHeaders(headers);
        }
    }

    protected String getServerErrorMessage(Response response) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            return ((SelectiveCoursesActivity) activity).getServerErrorMessage(response);
        }
        return "Error :(";
    }

    public void setExtendSelectiveCourseFragment(boolean isExtend) {
        if (recyclerView != null) {
            SelectiveCoursesAdapter adapter = (SelectiveCoursesAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                if (isExtend) {
                    adapter.setExtendedView();
                } else {
                    adapter.setShortView();
                }
            }
        }
    }

    public void sort(Comparator<SelectiveCourse> comparator) {
        SelectiveCourses selectiveCourses = showingSelectiveCourses;

        Collections.sort(selectiveCourses.getSelectiveCoursesFirstSemester(), comparator);
        Collections.sort(selectiveCourses.getSelectiveCoursesSecondSemester(), comparator);

        updateRecyclerView();
    }

    protected void updateRecyclerView() {
        SelectiveCoursesAdapter adapter = (SelectiveCoursesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public SelectiveCoursesAdapter getCurrentAdapter() {
        if (recyclerView != null) {
            return (SelectiveCoursesAdapter) recyclerView.getAdapter();
        }
        return null;
    }
}
