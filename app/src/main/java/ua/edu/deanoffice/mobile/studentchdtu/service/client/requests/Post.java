package ua.edu.deanoffice.mobile.studentchdtu.service.client.requests;

import android.util.Log;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.Mobile;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.UserData.Credentials;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces.GetUserData;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.interfaces.PostRequest;

public class Post {

    private Retrofit retrofit;
    private Response response;
    private boolean isSuccesful = false;

    public boolean isSuccesful() {
        return isSuccesful;
    }

    public Response getResponse() {
        return response;
    }

    public Post(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void sendCredentials(Credentials cred) {
        PostRequest post = retrofit.create(PostRequest.class);
        try{
            Response resp = post.request(cred).execute();
            Log.d("Test", "Code: " + resp.code());
            response = resp;
            isSuccesful = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
