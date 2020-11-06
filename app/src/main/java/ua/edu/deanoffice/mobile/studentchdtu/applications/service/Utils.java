package ua.edu.deanoffice.mobile.studentchdtu.applications.service;

import com.google.gson.Gson;

import ua.edu.deanoffice.mobile.studentchdtu.applications.model.RenewApplicationData;
import ua.edu.deanoffice.mobile.studentchdtu.applications.model.RetakeApplicationData;

public class Utils {

    private final static char[] notValidChars = {'=', '/', ' ', '-', '+'};

    public static boolean isStringValid(String string) {
        if (string.isEmpty()) {
            return false;
        }

        for (char ch : notValidChars) {
            if (string.contains("" + ch)) {
                return false;
            }
        }
        return true;
    }

    public static String retakeApplicationDataToJSON(RetakeApplicationData retakeApplicationData) {
        return new Gson().toJson(retakeApplicationData);
    }

    public static String renewApplicationDataToJSON(RenewApplicationData renewApplicationData) {
        return new Gson().toJson(renewApplicationData);
    }
}
