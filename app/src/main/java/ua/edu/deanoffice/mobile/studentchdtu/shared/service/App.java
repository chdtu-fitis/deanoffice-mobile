package ua.edu.deanoffice.mobile.studentchdtu.shared.service;

import ua.edu.deanoffice.mobile.studentchdtu.applications.ApplicationCache;
import ua.edu.deanoffice.mobile.studentchdtu.shared.network.Client;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.model.JWToken;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.Student;

public class App {
    private static App instance = new App();
    public static App getInstance(){
        return instance;
    }

    private Client client;
    private ApplicationCache currentApplication;
    private Student currentStudent;

    public JWToken jwt;

    public Client getClient() {
        return client;
    }

    public ApplicationCache getCurrentApplication() {
        return currentApplication;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    private App() {
        client = new Client();
        currentApplication = new ApplicationCache();
    }
}
