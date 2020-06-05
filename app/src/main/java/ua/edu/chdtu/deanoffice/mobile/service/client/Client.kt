package ua.edu.chdtu.deanoffice.mobile.service.client

import okhttp3.OkHttpClient
import ua.edu.chdtu.deanoffice.mobile.service.client.requests.Get
import ua.edu.chdtu.deanoffice.mobile.service.client.requests.Response
import ua.edu.chdtu.deanoffice.mobile.service.threading.ThreadData

import java.util.*

class Client() {

    var okHttpClient = OkHttpClient()
    var threadRequest: Thread ?= null;

    fun emptyGet() : Get{
        var getRequest = Get(okHttpClient)
        threadRequest = Thread{ getRequest.run()}
        threadRequest!!.start()
        while(!getRequest.isGet){

        }
        return getRequest
    }

}