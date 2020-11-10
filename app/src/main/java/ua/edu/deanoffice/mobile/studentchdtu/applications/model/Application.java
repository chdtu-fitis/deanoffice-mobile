package ua.edu.deanoffice.mobile.studentchdtu.applications.model;

public class Application {
    private String header;
    private String body;

    public Application(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }
}
