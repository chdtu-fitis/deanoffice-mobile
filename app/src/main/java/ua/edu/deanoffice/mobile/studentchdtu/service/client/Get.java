package ua.edu.deanoffice.mobile.studentchdtu.service.client;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces.GetApplication;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces.GetApplicationTypeList;

public class Get {

    private Retrofit retrofit;
    public String response;
    public boolean isGet;

    public Get(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void applicationTypeList() {
        GetApplicationTypeList req = retrofit.create(GetApplicationTypeList.class);
        try{
            Response resp = req.getApplicationTypeList().execute();
            response = ((ResponseBody)resp.body()).string();

        }catch (IOException e){
            e.printStackTrace();
        }
        isGet = true;
    }

    public void application(int id, String json) {
        GetApplication req = retrofit.create(GetApplication.class);
        try{
            Response resp = req.getRequest(id, json).execute();
            response = ((ResponseBody)resp.body()).string();
        }catch (IOException e){
            e.printStackTrace();
        }
        isGet = true;
    }

}
