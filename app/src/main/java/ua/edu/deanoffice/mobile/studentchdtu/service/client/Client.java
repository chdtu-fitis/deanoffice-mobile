package ua.edu.deanoffice.mobile.studentchdtu.service.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Client instance = new Client();

    private Retrofit retrofitBase = new Retrofit.Builder()
            .baseUrl("http://192.168.88.206:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Client getInstance() {
        return instance;
    }

    public Get getApplicationList() {
        final Get getRequest = new Get(retrofitBase);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getRequest.applicationTypeList();
            }
        }).start();
        while(!getRequest.isGet){}
        return getRequest;
    }

    public Get getApplication(final int id, final String json) {
        final Get getRequest = new Get(retrofitBase);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getRequest.application(id, json);
            }
        }).start();
        while(!getRequest.isGet){}
        return getRequest;
    }
}
