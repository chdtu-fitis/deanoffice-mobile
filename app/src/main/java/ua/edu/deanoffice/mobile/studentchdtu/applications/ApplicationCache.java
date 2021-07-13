package ua.edu.deanoffice.mobile.studentchdtu.applications;

import androidx.annotation.Keep;

import ua.edu.deanoffice.mobile.studentchdtu.applications.model.Application;

@Keep
public class ApplicationCache {
    private Application application;

    public String getHeader() {
        return application.getHeader().replace("\\n", "\n");
    }

    public String getBody() {
        return "\t\t" + application.getBody();
    }

    public void load(Application application) {
        this.application = application;
    }
}
