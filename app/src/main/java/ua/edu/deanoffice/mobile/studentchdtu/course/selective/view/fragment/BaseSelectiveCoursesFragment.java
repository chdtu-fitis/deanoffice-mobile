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

import lombok.Getter;
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

import static ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.SelectiveCoursesAdapter.ViewHolder;
import static ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.SelectiveCoursesAdapter.bindViewHolder;
import static ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.SelectiveCoursesAdapter.createViewHolder;
import static ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.SelectiveCoursesAdapter.hasGeneralAndProfessional;

public abstract class BaseSelectiveCoursesFragment extends Fragment {
    public enum FragmentState {
        FIRST_SEMESTER("В меню", "Далі"),
        SECOND_SEMESTER("Назад", "Далі"),
        CONFIRM_SCREEN("Назад", "Підтвердити");
        @Getter
        private final String leftButtonLabel, rightButtonLabel;

        FragmentState(String leftButtonLabel, String rightButtonLabel) {
            this.leftButtonLabel = leftButtonLabel;
            this.rightButtonLabel = rightButtonLabel;
        }
    }

    protected SelectedCoursesCounter selectedCoursesCounter;
    protected RecyclerView recyclerView;
    protected SelectiveCoursesAdapter adapterFirstSemester, adapterSecondSemester;
    protected SelectiveCourses showingSelectiveCourses;
    protected Button leftControlButton, rightControlButton;
    protected FragmentState fragmentState;

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

        leftControlButton = view.findViewById(R.id.left_control_button);
        rightControlButton = view.findViewById(R.id.right_control_button);

        initAdapters();

        selectedCoursesCounter.setSelectListener(new SelectedCoursesCounter.SelectListener() {
            @Override
            public void onAllSelected() {
                if (fragmentState == FragmentState.SECOND_SEMESTER) {
                    enableButton(rightControlButton);
                }
            }

            @Override
            public void onNotAllSelected() {
                if (fragmentState == FragmentState.SECOND_SEMESTER) {
                    disableButton(rightControlButton);
                }
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

        fragmentState = FragmentState.FIRST_SEMESTER;
        if (recyclerView != null) {
            SelectiveCoursesAdapter adapter = (SelectiveCoursesAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                if (adapter.getSemester() == Semester.SECOND) {
                    fragmentState = FragmentState.SECOND_SEMESTER;
                }
            }
        }
        changeFragmentState(fragmentState);
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

    protected void changeFragmentState(FragmentState state) {
        if (fragmentState == FragmentState.CONFIRM_SCREEN) {
            hideSelectiveCoursesListToConfirm();
        }
        fragmentState = state;
        switch (state) {
            case FIRST_SEMESTER:
                showFirstSemester();
                showControlButtons(FragmentState.FIRST_SEMESTER);
                break;
            case SECOND_SEMESTER:
                showSecondSemester();
                showControlButtons(FragmentState.SECOND_SEMESTER);
                break;
            case CONFIRM_SCREEN:
                showSelectiveCoursesListToConfirm();
                showControlButtons(FragmentState.CONFIRM_SCREEN);
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

        if (selectedCoursesCounter != null) {
            selectedCoursesCounter.switchSemester(Semester.FIRST);
        }
    }

    protected void showSecondSemester() {
        if (showingSelectiveCourses == null) return;
        if (adapterSecondSemester == null) {
            List<SelectiveCourse> selectiveCourseList = showingSelectiveCourses.getSelectiveCoursesSecondSemester();
            adapterSecondSemester = new SelectiveCoursesAdapter(selectiveCourseList, selectedCoursesCounter, Semester.SECOND);
        }
        recyclerView.setAdapter(adapterSecondSemester);

        if (selectedCoursesCounter != null) {
            selectedCoursesCounter.switchSemester(Semester.SECOND);
        }
    }

    protected void showSelectiveCoursesListToConfirm() {
        showHeaders(SelectiveCoursesActivity.Headers.CONFIRM);

        ViewGroup viewGroup = getView().findViewById(R.id.сontainer_сonfirmed_сourses);
        fillSelectedSelectiveCoursesContainer(viewGroup);
        recyclerView.setVisibility(View.GONE);
    }

    protected void fillSelectedSelectiveCoursesContainer(ViewGroup viewGroup){
        viewGroup.removeAllViews();

        SemesterLabel firstSemesterLabel = new SemesterLabel(Semester.FIRST);
        SemesterLabel secondSemesterLabel = new SemesterLabel(Semester.SECOND);

        List<SelectiveCourse> firstSemesterCoursesList = showingSelectiveCourses.getSelectiveCoursesFirstSemester();
        List<SelectiveCourse> secondSemesterCoursesList = showingSelectiveCourses.getSelectiveCoursesSecondSemester();
        final boolean showTrainingCycle = hasGeneralAndProfessional(showingSelectiveCourses.getSelectiveCoursesBothSemesters());

        View firstSemesterLabelView = firstSemesterLabel.onCreateView(getContext(), viewGroup);
        viewGroup.addView(firstSemesterLabelView);
        for (SelectiveCourse course : firstSemesterCoursesList) {
            if (course.isSelected() && course.isAvailable()) {
                ViewHolder viewHolder = createViewHolder(viewGroup);
                bindViewHolder(viewHolder, course, showTrainingCycle);
                viewHolder.setInteractive(false);
                viewHolder.setExtendedView();
                viewHolder.setVisible(true);
                viewGroup.addView(viewHolder.itemView);
            }
        }

        View secondSemesterLabelView = secondSemesterLabel.onCreateView(getContext(), viewGroup);
        viewGroup.addView(secondSemesterLabelView);
        for (SelectiveCourse course : secondSemesterCoursesList) {
            if (course.isSelected() && course.isAvailable()) {
                ViewHolder viewHolder = createViewHolder(viewGroup);
                bindViewHolder(viewHolder, course, showTrainingCycle);
                viewHolder.setInteractive(false);
                viewHolder.setExtendedView();
                viewHolder.setVisible(true);
                viewGroup.addView(viewHolder.itemView);
            }
        }

        viewGroup.setVisibility(View.VISIBLE);
    }

    protected void hideSelectiveCoursesListToConfirm() {
        showHeaders(SelectiveCoursesActivity.Headers.SELECTION);

        ViewGroup viewGroup = getView().findViewById(R.id.сontainer_сonfirmed_сourses);
        recyclerView.setVisibility(View.VISIBLE);
        viewGroup.setVisibility(View.GONE);
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

    /*
     * Setup Control Buttons
     */
    protected void showControlButtons(FragmentState state) {
        leftControlButton.setText(state.getLeftButtonLabel());
        rightControlButton.setText(state.getRightButtonLabel());
        fragmentState = state;

        enableButton(rightControlButton);

        BaseControlButtonsHandler controlButtonsHandler;
        switch (state) {
            case SECOND_SEMESTER:
                controlButtonsHandler = new ControlButtonSecondSemester();
                if (!selectedCoursesCounter.hasAllSelected()) {
                    disableButton(rightControlButton);
                }
                break;
            case CONFIRM_SCREEN:
                controlButtonsHandler = new ControlButtonConfirm();
                break;
            case FIRST_SEMESTER:
            default:
                controlButtonsHandler = new ControlButtonFirstSemester();
        }

        leftControlButton.setOnClickListener(controlButtonsHandler::onClickLeftButton);
        rightControlButton.setOnClickListener(controlButtonsHandler::onClickRightButton);
    }

    abstract static class BaseControlButtonsHandler {
        protected abstract void onClickLeftButton(View button);

        protected abstract void onClickRightButton(View button);
    }

    class ControlButtonFirstSemester extends BaseControlButtonsHandler {

        @Override
        protected void onClickLeftButton(View button) {
            onBackPressed();
        }

        @Override
        protected void onClickRightButton(View button) {
            changeFragmentState(FragmentState.SECOND_SEMESTER);
        }
    }

    class ControlButtonSecondSemester extends BaseControlButtonsHandler {

        @Override
        protected void onClickLeftButton(View button) {
            changeFragmentState(FragmentState.FIRST_SEMESTER);
        }

        @Override
        protected void onClickRightButton(View button) {
            if (selectedCoursesCounter.confirmIsAvailable()) {
                changeFragmentState(FragmentState.CONFIRM_SCREEN);
            } else {
                Snackbar.make(button.findViewById(android.R.id.content), getRString(R.string.worn_select_courses), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        }
    }

    class ControlButtonConfirm extends BaseControlButtonsHandler {

        @Override
        protected void onClickLeftButton(View button) {
            changeFragmentState(FragmentState.SECOND_SEMESTER);
        }

        @Override
        protected void onClickRightButton(View button) {
            showHeaders(SelectiveCoursesActivity.Headers.CONFIRM);

            List<SelectiveCoursesAdapter> adaptersList = new ArrayList<>();
            adaptersList.add(adapterFirstSemester);
            adaptersList.add(adapterSecondSemester);
            SelectiveCourses selectiveCoursesFinal = new SelectiveCourses();
            for (SelectiveCoursesAdapter adapter : adaptersList) {
                if (adapter != null) {
                    if (adapter.getSemester() == Semester.FIRST) {
                        selectiveCoursesFinal.setSelectiveCoursesFirstSemester(adapter.getSelectedCourse());
                    } else {
                        selectiveCoursesFinal.setSelectiveCoursesSecondSemester(adapter.getSelectedCourse());
                    }
                    saveUserChoice(selectiveCoursesFinal);
                } else {
                    showError(getRString(R.string.error_null_selective_courses_adapter));
                }
            }
        }
    }
    /*
     * End Setup Control Buttons
     */

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

    protected void onBackPressed() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }
}
