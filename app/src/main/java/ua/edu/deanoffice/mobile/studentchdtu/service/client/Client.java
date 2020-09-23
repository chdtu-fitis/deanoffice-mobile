package ua.edu.deanoffice.mobile.studentchdtu.service.client;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.requests.Get;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.requests.Post;

public class Client {

    private Retrofit retrofitBase;
    private Executor executor;

    public Client() {
        retrofitBase = new Retrofit.Builder()
                .baseUrl("http://192.168.1.126:8080/")
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

    public interface OnResponseGetCallback {
        void onResponseGet(Get get);
    }
    public interface OnResponsePostCallback {
        void onResponsePost(Post get);
    }
}
