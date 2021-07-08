package ua.edu.deanoffice.mobile.studentchdtu.user.profile.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity.MainMenuActivity;

public class MainMenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mainmenu, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        Button buttonSelectiveCourses = view.findViewById(R.id.buttonSelectiveCourses);
        Button buttonSchedule = view.findViewById(R.id.buttonSchedule);

        MainMenuActivity activity = (MainMenuActivity) getActivity();
        if (activity == null || !activity.isAccessSuccess()) {
            buttonSelectiveCourses.setEnabled(false);
            view.findViewById(R.id.buttonContainerSelectiveCourses).setAlpha(0.5f);
        }

        buttonSelectiveCourses.setOnClickListener(button -> {
            activity.onMainMenuItemClick(R.id.nav_selectivecourses);
        });
        buttonSchedule.setOnClickListener(button -> {
            Toast.makeText(getContext(), "Coming soon", Toast.LENGTH_SHORT).show();
        });
    }
}
