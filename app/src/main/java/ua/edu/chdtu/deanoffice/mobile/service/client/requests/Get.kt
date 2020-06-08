package ua.edu.chdtu.deanoffice.mobile.service.client.requests

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit

class Get(private var retrofit: Retrofit) : Runnable{

    var response: Response ?= null;
    var isGet: Boolean = false;

    public override fun run(){
        var req = retrofit.create(GetRequest::class.java)
        var resp = req.getRequest().execute()
        response = Response(resp.body()!!.string())
        isGet = true;
    }
}