package ua.edu.deanoffice.mobile.studentchdtu.service;

import com.google.gson.Gson;

import ua.edu.deanoffice.mobile.studentchdtu.mobile.UserData.Credentials;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.Application;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.RenewApplicationData;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.RetakeApplicationData;

public class Utils {

    private final static char[] notValidChars = { '=', '/', ' ', '-', '+'};

    public static boolean isStringValid(String string) {
        if(string.isEmpty()) {
            return false;
        }

        for (char ch : notValidChars) {
            if(string.contains(""+ch)) {
                return false;
            }
        }
        return true;
    }

    public static Application JSONtoApplication (String json) {
        return new Gson().fromJson(json, Application.class);
    }

    public static String retakeApplicationDataToJSON(RetakeApplicationData retakeApplicationData) {
        return new Gson().toJson(retakeApplicationData);
    }

    public static String renewApplicationDataToJSON(RenewApplicationData renewApplicationData) {
        return new Gson().toJson(renewApplicationData);
    }

    public static String userCredentialsToJSON(Credentials credentials) {
        return new Gson().toJson(credentials);
    }

}
