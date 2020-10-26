package ua.edu.deanoffice.mobile.studentchdtu.mobile;

import ua.edu.deanoffice.mobile.studentchdtu.service.client.Client;
import ua.edu.deanoffice.mobile.studentchdtu.service.model.student.Student;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.JWToken;

public class Mobile {
    private static Mobile instance = new Mobile();
    public static Mobile getInstance(){
        return instance;
    }

    private Client client;
    private ApplicationCache currentApplication;
    private Student student;

    public JWToken jwToken;

    public Client getClient() {
        return client;
    }

    public ApplicationCache getCurrentApplication() {
        return currentApplication;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    private Mobile() {
        client = new Client();
        currentApplication = new ApplicationCache();
    }

}
