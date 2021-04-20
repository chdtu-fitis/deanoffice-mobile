package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ConfirmedSelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ExistingId;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.StudentDegreeSelectiveCoursesIds;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.service.SelectiveCourseRequests;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.ChdtuAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.SelectiveCoursesAdapter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity.SelectiveCoursesActivity;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;

public class SelectiveCoursesSecondRoundFragment extends Fragment {
    private static final String LOG_TAG = "SelectiveCoursesSecondRoundFragment";

    private TextView textSelectiveCoursesCounter;

    public SelectiveCoursesSecondRoundFragment() {

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
        //Load data
        loadAvailableSelectiveCourses();
    }

    public void loadAvailableSelectiveCourses() {
        showLoadingProgress();

        App.getInstance()
                .getClient()
                .createRequest(SelectiveCourseRequests.class)
                .requestSelectiveCourses(
                        App.getInstance().getJwt().getToken(),
                        App.getInstance().getCurrentStudent().getDegrees()[0].getId())
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

    private void disableButton(View button) {
        button.setAlpha(0.5f);
        button.setEnabled(false);
    }

    private void enableButton(View button) {
        button.setAlpha(1f);
        button.setEnabled(true);
    }

    private void fillSelectiveCoursesList(SelectiveCourses selectiveCourses) {
        if (getView() == null) return;
        RecyclerView recyclerView = getView().findViewById(R.id.listview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        boolean isMagister = App.getInstance().getCurrentStudent().getDegrees()[0].getSpecialization().getDegree().getId() == 3;

        Button clearButton = getView().findViewById(R.id.clear_selectivecourses);
        Button confirmButton = getView().findViewById(R.id.confirm_selectivecourses);

        SelectiveCoursesAdapter adapter = new SelectiveCoursesAdapter(selectiveCourses, getFragmentManager(), textSelectiveCoursesCounter, true, isMagister);
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
        confirmButton.setOnClickListener((view) -> {

            if (adapter.getSelectedCourseFirstSemester().size() == adapter.getMaxCoursesFirstSemester() &&
                    adapter.getSelectedCourseSecondSemester().size() == adapter.getMaxCoursesSecondSemester()) {

                textSelectiveCoursesCounter.setText(getRString(R.string.info_confirm_sel_courses));
                confirmButton.setText(getRString(R.string.button_confirm));
                clearButton.setText(getRString(R.string.button_cancel));

                clearButton.setOnClickListener((button) -> {
                    Intent intent = new Intent(getContext(), SelectiveCoursesActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                });

                SelectiveCourses selectiveCoursesFinal = new SelectiveCourses();
                selectiveCoursesFinal.setSelectiveCoursesFirstSemester(adapter.getSelectedCourseFirstSemester());
                selectiveCoursesFinal.setSelectiveCoursesSecondSemester(adapter.getSelectedCourseSecondSemester());

                ChdtuAdapter adapterFinal = new ChdtuAdapter(selectiveCoursesFinal, getFragmentManager(), null, false);
                recyclerView.setAdapter(adapterFinal);
                adapterFinal.disableCheckBoxes();

                confirmButton.setOnClickListener((viewConfirm) -> {
                    saveUserChose(selectiveCoursesFinal);
                });
            } else {
                Snackbar.make(view.findViewById(android.R.id.content), getRString(R.string.worn_select_courses), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }

    private void saveUserChose(SelectiveCourses selectiveCourses) {
        showLoadingProgress();

        ConfirmedSelectiveCourses confirmedSelectiveCourses = new ConfirmedSelectiveCourses();
        confirmedSelectiveCourses.setSelectiveCourses(selectiveCourses.getSelectiveCoursesIds());
        ExistingId existingId = new ExistingId(App.getInstance().getCurrentStudent().getDegrees()[0].getId());
        confirmedSelectiveCourses.setStudentDegreeId(existingId);

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

    private String getRString(int stringResource) {
        if (getContext() != null) {
            return getContext().getResources().getString(stringResource);
        }
        return "";
    }

    public void showError(String msg) {
        if (getView() != null && getContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            ViewGroup viewGroup = getView().findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(getContext())
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
    }

    private void showLoadingProgress() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((SelectiveCoursesActivity) activity).showLoadingProgress();
        }
    }

    private void hideLoadingProgress() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((SelectiveCoursesActivity) activity).hideLoadingProgress();
        }
    }
}