package ua.edu.deanoffice.mobile.studentchdtu.shared.network;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private Retrofit retrofitBase;
    private Executor executor;
    private Requests requests;

    public Client() {
        retrofitBase = new Retrofit.Builder()
                .baseUrl("http://25.19.241.234:8075/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        executor = Executors.newCachedThreadPool();
        requests = retrofitBase.create(Requests.class);
    }

    public Requests getRequests() {
        return requests;
    }
}