package ua.edu.deanoffice.mobile.studentchdtu.user.login.model;


import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.ValidModel;

public class JWToken extends ValidModel {

    private String token;

    public JWToken() {
    }

    public JWToken(String token) {
        this.token = token;
    }

    @Override
    public boolean isValid() {
        return !(token.isEmpty() || token == null);
    }

    public String getToken() {
        return token;
    }
}
