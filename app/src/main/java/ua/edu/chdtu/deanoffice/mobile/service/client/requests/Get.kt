package ua.edu.chdtu.deanoffice.mobile.service.client.requests

import okhttp3.OkHttpClient
import okhttp3.Request

class Get(private var okHttpClient: OkHttpClient) : Runnable{

    var response: Response ?= null;
    var isGet: Boolean = false;

    public override fun run(){
        val url = "http://25.49.24.181:8080/application"
        val req = Request.Builder().url(url).build()

        var resp = okHttpClient.newCall(req).execute()
        response = Response(resp.body!!.string())
        isGet = true;
    }
}