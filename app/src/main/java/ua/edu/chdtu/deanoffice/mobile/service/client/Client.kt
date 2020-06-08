package ua.edu.chdtu.deanoffice.mobile.service.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.edu.chdtu.deanoffice.mobile.service.POJO.ApplicationTypeIdPOJO
import ua.edu.chdtu.deanoffice.mobile.service.client.requests.Get
import ua.edu.chdtu.deanoffice.mobile.service.client.requests.Post

class Client() {

    private var retrofitBase = Retrofit.Builder()
        .baseUrl("http://25.49.24.181:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var threadRequest: Thread ?= null;

    fun emptyGet() : Get{
        var getRequest = Get(retrofitBase)
        threadRequest = Thread{ getRequest.run()}
        threadRequest!!.start()
        while(!getRequest.isGet){ /*process status*/ }
        return getRequest
    }

    fun get() : Get{
        var getRequest = Get(retrofitBase)
        threadRequest = Thread{ getRequest.run()}
        threadRequest!!.start()
        while(!getRequest.isGet){ /*process status*/ }
        return getRequest
    }

    fun post(ApplicationTypeId: ApplicationTypeIdPOJO) : Post{
        var postRequest = Post(retrofitBase)
        threadRequest = Thread{ postRequest.run(ApplicationTypeId)}
        threadRequest!!.start()
        while(!postRequest.isGet){ /*process status*/ }
        return postRequest
    }


}