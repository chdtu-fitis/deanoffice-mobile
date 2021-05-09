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

import java.util.Collections;
import java.util.Comparator;

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
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.service.SelectiveCourseRequests;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.SelectiveCoursesAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity.SelectiveCoursesActivity;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;

public abstract class BaseSelectiveCoursesFragment extends Fragment {
    protected SelectedCoursesCounter selectedCoursesCounter;
    protected RecyclerView recyclerView;
    protected SelectiveCourses showingSelectiveCourses;
    protected Button clearButton, confirmButton;
    protected View toFirstSemesterButton, toSecondSemesterButton;

    public BaseSelectiveCoursesFragment(SelectedCoursesCounter selectedCoursesCounter) {
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

        toFirstSemesterButton.setOnClickListener(v -> selectSemester(1));
        toSecondSemesterButton.setOnClickListener(v -> selectSemester(2));

        selectSemester(1);

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

        fillSelectiveCoursesList();
    }

    protected void fillSelectiveCoursesList() {
        if (showingSelectiveCourses == null) return;

        SelectiveCoursesAdapter adapter = new SelectiveCoursesAdapter(showingSelectiveCourses, getFragmentManager(), selectedCoursesCounter);
        recyclerView.setAdapter(adapter);

        clearButton.setOnClickListener((v) -> adapter.clearSelected());
        confirmButton.setOnClickListener(this::onClickConfirmButton);
    }

    //Go from confirm mode to normal list
    protected void onClickButtonBackFromConfirmFragment(View button) {
        //Change Headers
        showHeaders(SelectiveCoursesActivity.Headers.SELECTION);

        //Rename Buttons
        confirmButton.setText(getRString(R.string.button_next));
        clearButton.setText(getRString(R.string.button_uncheck_selection));

        SelectiveCoursesAdapter adapter = (SelectiveCoursesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            //Show hidden courses in list
            adapter.showAllHiddenCourseFragments();

            //Change ClearButton onClickListener
            clearButton.setOnClickListener((v) -> adapter.clearSelected());

            //Change ConfirmButton onClickListener
            confirmButton.setOnClickListener(this::onClickConfirmButton);

            //Unblocked Interactive With CheckBox
            adapter.disableCheckBoxes(false);
        }
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

            SelectiveCoursesAdapter adapter = (SelectiveCoursesAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                //Hide unchecked and disqualified courses in list
                adapter.hideAllUncheckedCourseFragments();

                //Change ConfirmButton onClickListener
                SelectiveCourses selectiveCoursesFinal = new SelectiveCourses();
                selectiveCoursesFinal.setSelectiveCoursesFirstSemester(adapter.getSelectedCourseFirstSemester());
                selectiveCoursesFinal.setSelectiveCoursesSecondSemester(adapter.getSelectedCourseSecondSemester());

                confirmButton.setOnClickListener((viewConfirm) -> saveUserChoice(selectiveCoursesFinal));

                //Blocked Interactive With CheckBox
                adapter.disableCheckBoxes(true);
            }
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

    protected void selectSemester(int semester) {
        switch (semester) {
            case 2:
                toFirstSemesterButton.setVisibility(View.VISIBLE);
                toSecondSemesterButton.setVisibility(View.GONE);
                break;
            case 1:
            default:
                toSecondSemesterButton.setVisibility(View.VISIBLE);
                toFirstSemesterButton.setVisibility(View.GONE);
        }
    }

    protected void showFirstSemester(){

    }

    protected void showSecondSemester(){

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
}
