package ua.edu.deanoffice.mobile.studentchdtu.mobile;

import ua.edu.deanoffice.mobile.studentchdtu.service.client.Client;

public class Mobile {
    private static Mobile instance = new Mobile();
    public static Mobile getInstance(){
        return instance;
    }

    public Client client;
    public ApplicationCache currentApplication;

    public Mobile() {
        client = new Client();
        currentApplication = new ApplicationCache();
    }



}
