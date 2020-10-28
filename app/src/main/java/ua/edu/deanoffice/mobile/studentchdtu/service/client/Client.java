package ua.edu.deanoffice.mobile.studentchdtu.service.client;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.UserData.Credentials;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.requests.Get;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.requests.Post;

public class Client {

    private Retrofit retrofitBase;
    private Executor executor;

    public Client() {
        retrofitBase = new Retrofit.Builder()
                .baseUrl("http://25.19.241.234:8075/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        executor = Executors.newCachedThreadPool();
    }

    public void getApplicationList(OnResponseCallback onResponseGetCallback) {
        Get getRequest = new Get(retrofitBase);

        executor.execute(() -> {
            getRequest.applicationTypeList();
            if(getRequest.isSuccesful()) {
                onResponseGetCallback.onResponseSuccess(getRequest.getResponse());
            }else{
                onResponseGetCallback.onResponseFailure(getRequest.getResponse());
            }
        });
    }

    public void getApplication(final int id, final String json, OnResponseCallback onResponseGetCallback) {
        Get getRequest = new Get(retrofitBase);

        executor.execute(() -> {
            getRequest.application(id, json);
            if(getRequest.isSuccesful()) {
                onResponseGetCallback.onResponseSuccess(getRequest.getResponse());
            }else{
                onResponseGetCallback.onResponseFailure(getRequest.getResponse());
            }
        });
    }

    public void getUser(Credentials credentials, OnResponseCallback onResponsePostCallback) {
        Post postRequest = new Post(retrofitBase);
        executor.execute(() -> {
            postRequest.sendCredentials(credentials);
            if(postRequest.isSuccesful()) {
                onResponsePostCallback.onResponseSuccess(postRequest.getResponse());
            }else{
                onResponsePostCallback.onResponseFailure(postRequest.getResponse());
            }
        });
    }

    public void getUserData(OnResponseCallback onResponsePostCallback) {
        Get getRequest = new Get(retrofitBase);
        executor.execute(() -> {
            getRequest.getUserData();
            if(getRequest.isSuccesful()) {
                onResponsePostCallback.onResponseSuccess(getRequest.getResponse());
            }else {
                onResponsePostCallback.onResponseFailure(getRequest.getResponse());
            }
        });
    }

    public interface OnResponseCallback {
        void onResponseSuccess(ResponseBody response);
        void onResponseFailure(ResponseBody response);
    }
}