package ua.edu.deanoffice.mobile.studentchdtu.user.login.model;

import org.json.JSONException;
import org.json.JSONObject;

import ua.edu.deanoffice.mobile.studentchdtu.Utils;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.ValidModel;

public class JWToken extends ValidModel {

    public enum UserRole {
        ROLE_STUDENT,
        ROLE_STUD_IN_ACADEMIC_VACATION
    }

    private final String token;

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

    public String[] getTokenElements() {
        return getToken().split("\\.");
    }

    public UserRole getUserRole() {
        UserRole userRole = null;
        String tokenString = Utils.decryptBase64(getTokenElements()[1]);
        try {
            JSONObject tokenJson = new JSONObject(tokenString);
            userRole = UserRole.valueOf(tokenJson.getJSONArray("rol").get(0).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userRole;
    }
}
