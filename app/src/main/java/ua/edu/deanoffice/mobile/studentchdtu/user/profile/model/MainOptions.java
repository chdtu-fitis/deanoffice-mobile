package ua.edu.deanoffice.mobile.studentchdtu.user.profile.model;

import lombok.Data;

@Data
public class MainOptions {
    //Constance IDs
    public final static String APP_MAIN_OPTIONS = "application_main_options";
    public final static String ENABLE_NOTIF_ON_REG_SELECTIVE_COURSES = "enable_notifications_on_reg_selective_courses";

    //Options with default states
    private boolean enableNotifOnRegSelectiveCourses = true;
}
