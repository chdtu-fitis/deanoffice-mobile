package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity.SelectiveCoursesActivity;

public abstract class BaseSelectiveCoursesFragment extends Fragment {
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

    protected String getRString(int stringResource) {
        if (getContext() != null) {
            return getContext().getResources().getString(stringResource);
        }
        return "";
    }
}
