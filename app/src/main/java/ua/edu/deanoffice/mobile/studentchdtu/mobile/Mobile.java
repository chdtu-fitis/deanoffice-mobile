package ua.edu.deanoffice.mobile.studentchdtu.mobile;

import ua.edu.deanoffice.mobile.studentchdtu.mobile.UserData.User;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.Client;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.Token;

public class Mobile {
    private static Mobile instance = new Mobile();
    public static Mobile getInstance(){
        return instance;
    }

    private Client client;
    private ApplicationCache currentApplication;
    private User user;

    public Token token;

    public Client getClient() {
        return client;
    }

    public ApplicationCache getCurrentApplication() {
        return currentApplication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private Mobile() {
        client = new Client();
        currentApplication = new ApplicationCache();
    }

}
