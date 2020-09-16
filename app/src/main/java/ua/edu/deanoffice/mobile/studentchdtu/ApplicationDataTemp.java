package ua.edu.deanoffice.mobile.studentchdtu;

public class ApplicationDataTemp {
    private static ApplicationDataTemp instance = new ApplicationDataTemp();

    public String header = "";
    public String body = "";

    public static ApplicationDataTemp getInstance(){
        return instance;
    }


}
