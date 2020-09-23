package ua.edu.deanoffice.mobile.studentchdtu.mobile;

import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.Application;

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
