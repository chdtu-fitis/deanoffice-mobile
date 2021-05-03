package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.BaseDrawerActivity;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.SelectedCoursesCounter;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ConfirmedSelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.ExistingId;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.SelectiveCourses;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.model.StudentDegreeSelectiveCoursesIds;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.service.SelectiveCourseRequests;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity.SelectiveCoursesActivity;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;

public abstract class BaseSelectiveCoursesFragment extends Fragment {
    protected SelectedCoursesCounter selectedCoursesCounter;

    public BaseSelectiveCoursesFragment(SelectedCoursesCounter selectedCoursesCounter) {
        this.selectedCoursesCounter = selectedCoursesCounter;
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

    protected void saveUserChoice(SelectiveCourses selectiveCourses) {
        showLoadingProgress();

        ConfirmedSelectiveCourses confirmedSelectiveCourses = new ConfirmedSelectiveCourses();
        confirmedSelectiveCourses.setSelectiveCourses(selectiveCourses.getSelectiveCoursesIds());

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
                            break;
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
