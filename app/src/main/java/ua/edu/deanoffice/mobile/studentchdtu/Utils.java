package ua.edu.deanoffice.mobile.studentchdtu;

import android.util.Base64;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

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

    public static String decryptBase64(String base64){
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        return new String(data, StandardCharsets.UTF_8);
        return "";
    }
}
