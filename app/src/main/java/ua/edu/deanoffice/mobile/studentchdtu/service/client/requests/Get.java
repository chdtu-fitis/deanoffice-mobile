package ua.edu.deanoffice.mobile.studentchdtu.service.client.requests;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.Mobile;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces.GetApplication;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces.GetApplicationTypeList;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces.GetUserData;

public class Get {

    private Retrofit retrofit;
    private ResponseBody response;
    private boolean isSuccesful = false;

    public boolean isSuccesful() {
        return isSuccesful;
    }

    public ResponseBody getResponse() {
        return response;
    }

    public Get(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void applicationTypeList() {
        GetApplicationTypeList req = retrofit.create(GetApplicationTypeList.class);
        try{
            Response resp = req.getApplicationTypeList().execute();
            response = (ResponseBody)resp.body();
            isSuccesful = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void application(int id, String json) {
        GetApplication req = retrofit.create(GetApplication.class);
        try{
            Response resp = req.getRequest(id, json).execute();
            response = (ResponseBody)resp.body();
            isSuccesful = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getUserData() {
        GetUserData post = retrofit.create(GetUserData.class);
        try{
            Response resp = post.getApplicationTypeList("Bearer " + Mobile.getInstance().jwt.token).execute();
            response = (ResponseBody)resp.body();
            isSuccesful = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
