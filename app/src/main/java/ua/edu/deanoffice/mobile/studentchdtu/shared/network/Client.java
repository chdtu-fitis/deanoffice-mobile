package ua.edu.deanoffice.mobile.studentchdtu.shared.network;

import androidx.annotation.Keep;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Keep
public class Client {

    private final Retrofit retrofitBase;

    public Client() {
        retrofitBase = new Retrofit.Builder()
                .baseUrl("http://192.168.0.106:8075/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T createRequest(Class<T> service) {
        return retrofitBase.create(service);
    }
}