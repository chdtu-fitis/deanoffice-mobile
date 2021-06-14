package ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.onesignal.OneSignal;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.BaseDrawerActivity;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.controller.OptionsController;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.MainOptions;

public class MainOptionsActivity extends BaseDrawerActivity implements CompoundButton.OnCheckedChangeListener {

    private MainOptions mainOptions;
    private OptionsController optionsController;

    //Fields
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchEnableNotifOnRegSelectiveCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Load xml for activity
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_main_options, mainContentBlock, false);
        mainContentBlock.addView(contentView, 1);

        getSupportActionBar().setTitle(getRString(R.string.action_bar_title_main_options));

        switchEnableNotifOnRegSelectiveCourses = findViewById(R.id.switchEnableNotifOnRegSelectiveCourses);
        switchEnableNotifOnRegSelectiveCourses.setOnCheckedChangeListener(this);
    }

    private void initFields() {
        switchEnableNotifOnRegSelectiveCourses.setChecked(mainOptions.isEnableNotifOnRegSelectiveCourses());
    }

    private void loadOptions() {
        mainOptions = optionsController.load();

        initFields();
    }

    private void saveOptions() {
        optionsController.save(mainOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mainOptions == null) {
            mainOptions = new MainOptions();
        }
        if (optionsController == null) {
            optionsController = new OptionsController(this);
        }
        loadOptions();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveOptions();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switchEnableNotifOnRegSelectiveCourses:
                mainOptions.setEnableNotifOnRegSelectiveCourses(isChecked);
                OneSignal.disablePush(!isChecked);
                break;
        }
    }
}