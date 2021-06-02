package ua.edu.deanoffice.mobile.studentchdtu.shared.service;

import lombok.Getter;
import lombok.Setter;
import ua.edu.deanoffice.mobile.studentchdtu.applications.ApplicationCache;
import ua.edu.deanoffice.mobile.studentchdtu.shared.network.Client;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.model.JWToken;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.Student;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.StudentDegree;

public class App {
    private static final App instance = new App();

    public static App getInstance() {
        return instance;
    }

    @Getter
    private final Client client;
    @Getter
    private final ApplicationCache currentApplication;
    @Getter
    private Student currentStudent;
    @Getter
    @Setter
    private StudentDegree selectedStudentDegree;
    @Getter
    @Setter
    private JWToken jwt;

    private App() {
        client = new Client();
        currentApplication = new ApplicationCache();
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
        selectedStudentDegree = currentStudent != null ? currentStudent.getDegrees()[0] : null;
    }
}
