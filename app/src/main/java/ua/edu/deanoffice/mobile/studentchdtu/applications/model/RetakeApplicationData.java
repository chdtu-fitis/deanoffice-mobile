package ua.edu.deanoffice.mobile.studentchdtu.applications.model;

import androidx.annotation.Keep;

@Keep
public class RetakeApplicationData {
    private final String course;
    private final int knowledge_control;

    public RetakeApplicationData(String course, int knowledge_control) {
        this.course = course;
        this.knowledge_control = knowledge_control;
    }
}
