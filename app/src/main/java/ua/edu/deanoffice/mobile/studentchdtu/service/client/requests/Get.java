package ua.edu.deanoffice.mobile.studentchdtu.service.client.requests;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces.GetApplication;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces.GetApplicationTypeList;

public class Get {

    private Retrofit retrofit;
    private String responseBody;

    public String getResponseBody() {
        return responseBody;
    }

    public Get(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void applicationTypeList() {
        GetApplicationTypeList req = retrofit.create(GetApplicationTypeList.class);
        try{
            Response resp = req.getApplicationTypeList().execute();
            responseBody = ((ResponseBody)resp.body()).string();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void application(int id, String json) {
        GetApplication req = retrofit.create(GetApplication.class);
        try{
            Response resp = req.getRequest(id, json).execute();
            responseBody = ((ResponseBody)resp.body()).string();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
