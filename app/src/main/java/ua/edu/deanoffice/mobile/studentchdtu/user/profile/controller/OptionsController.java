package ua.edu.deanoffice.mobile.studentchdtu.user.profile.controller;

import android.content.Context;
import android.content.SharedPreferences;

import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.MainOptions;

public class OptionsController {
    private final SharedPreferences sharedPreferences;

    public OptionsController(Context context) {
        sharedPreferences = context.getSharedPreferences(MainOptions.APP_MAIN_OPTIONS, Context.MODE_PRIVATE);
    }

    //Save and Load need update always when added new option
    public void save(MainOptions mainOptions) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(MainOptions.ENABLE_NOTIF_ON_REG_SELECTIVE_COURSES, mainOptions.isEnableNotifOnRegSelectiveCourses());

        editor.apply();
    }

    public MainOptions load() {
        MainOptions mainOptions = new MainOptions();

        String optionId = MainOptions.ENABLE_NOTIF_ON_REG_SELECTIVE_COURSES;
        if (sharedPreferences.contains(optionId)) {
            boolean value = sharedPreferences.getBoolean(optionId, mainOptions.isEnableNotifOnRegSelectiveCourses());
            mainOptions.setEnableNotifOnRegSelectiveCourses(value);
        }

        return mainOptions;
    }
}
