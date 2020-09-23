package ua.edu.deanoffice.mobile.studentchdtu.service.client.requests;

import retrofit2.Retrofit;

public class Post {

    private Retrofit retrofit;
    private String responseBody;

    public String getResponseBody() {
        return responseBody;
    }

    public Post(Retrofit retrofit) {
        this.retrofit = retrofit;
    }




}
