package ua.edu.deanoffice.mobile.studentchdtu.service;

import com.google.gson.Gson;

import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.Application;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.RenewApplicationData;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.RetakeApplicationData;

public class Utils {

    public static Application JSONtoApplication (String json) {
        return new Gson().fromJson(json, Application.class);
    }

    public static String retakeApplicationDataToJSON(RetakeApplicationData retakeApplicationData) {
        return new Gson().toJson(retakeApplicationData);
    }

    public static String renewApplicationDataToJSON(RenewApplicationData renewApplicationData) {
        return new Gson().toJson(renewApplicationData);
    }

}
