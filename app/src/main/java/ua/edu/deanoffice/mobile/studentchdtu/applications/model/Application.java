package ua.edu.deanoffice.mobile.studentchdtu.applications.model;

import androidx.annotation.Keep;

@Keep
public class Application {
    private final String header;
    private final String body;

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
