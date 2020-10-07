package ua.edu.deanoffice.mobile.studentchdtu.service.client;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
                .baseUrl("http://192.168.43.128:8075/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        executor = Executors.newCachedThreadPool();
    }

    public void getApplicationList(OnResponseGetCallback onResponseGetCallback) {
        Get getRequest = new Get(retrofitBase);

        executor.execute(()->{
            getRequest.applicationTypeList();
            onResponseGetCallback.onResponseGet(getRequest);
        });
    }

    public void getApplication(final int id, final String json, OnResponseGetCallback onResponseGetCallback) {
        Get getRequest = new Get(retrofitBase);

        executor.execute(()->{
            getRequest.application(id, json);
            onResponseGetCallback.onResponseGet(getRequest);
        });
    }

    public void getUser(Credentials credentials, OnResponsePostCallback onResponsePostCallback) {
        Post postRequest = new Post(retrofitBase);
        executor.execute(()->{
            postRequest.sendCredentials(credentials);
            onResponsePostCallback.onResponsePost(postRequest.getResponseBody());
        });
    }

    public void getUserData(OnResponsePostCallback onResponsePostCallback) {
        Post postRequest = new Post(retrofitBase);
        executor.execute(()->{
            postRequest.getUserData();
            onResponsePostCallback.onResponsePost(postRequest.getResponseBody());
        });
    }

    public interface OnResponseGetCallback {
        void onResponseGet(Get get);
    }

    public interface OnResponsePostCallback {
        void onResponsePost(String responseBody);
    }
}
