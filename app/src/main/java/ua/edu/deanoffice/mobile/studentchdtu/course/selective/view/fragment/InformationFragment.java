package ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity.MainMenuActivity;

public class InformationFragment extends Fragment {
    private final String informationMessage;
    private final boolean isVisibleMenuButton;

    public InformationFragment(String informationMessage, boolean withMenuButton) {
        this.informationMessage = informationMessage;
        this.isVisibleMenuButton = withMenuButton;
    }

    public InformationFragment(String informationMessage) {
        this(informationMessage, false);
    }

    public void menuButtonInit() {
        View menuButton = getView().findViewById(R.id.buttonToMenu);
        menuButton.setVisibility(isVisibleMenuButton ? View.VISIBLE : View.GONE);
        menuButton.setOnClickListener(button -> {
            Activity activity = getActivity();
            Intent mainMenuActivity = new Intent(activity, MainMenuActivity.class);
            startActivity(mainMenuActivity);
            activity.finish();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        menuButtonInit();
        if (informationMessage != null) {
            TextView informationTextView = view.findViewById(R.id.textInformationMessage);
            informationTextView.setText(informationMessage);
        }
    }
}