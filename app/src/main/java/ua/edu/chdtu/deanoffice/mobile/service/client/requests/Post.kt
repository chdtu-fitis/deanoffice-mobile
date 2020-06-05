package ua.edu.chdtu.deanoffice.mobile.service.client.requests

import okhttp3.OkHttpClient

class Post(private var okHttpClient: OkHttpClient) : Runnable{

    var response: Response ?= null;
    var isGet: Boolean = false;

    public override fun run() {

    }



}