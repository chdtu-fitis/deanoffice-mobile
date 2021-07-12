package ua.edu.deanoffice.mobile.studentchdtu.user.login.model;

import androidx.annotation.Keep;

@Keep
public class Credentials {
    private final String username;
    private final String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

}