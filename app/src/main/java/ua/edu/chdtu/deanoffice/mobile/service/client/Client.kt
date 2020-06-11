package ua.edu.chdtu.deanoffice.mobile.service.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.edu.chdtu.deanoffice.mobile.service.client.requests.Get

object Client {

    private var retrofitBase = Retrofit.Builder()
        .baseUrl("http://25.49.24.181:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun emptyGet() : Get{
        var getRequest = Get(retrofitBase)
        Thread{ getRequest.applicationTypeList()}.start()
        while(!getRequest.isGet){ /*process status*/ }
        return getRequest
    }

    fun getApplicationList() : Get{
        var getRequest = Get(retrofitBase)
        Thread{ getRequest.applicationTypeList()}.start()
        while(!getRequest.isGet){ /*process status*/ }
        return getRequest
    }

    fun getApplication(id: Int, json: String) : Get{
        var getRequest = Get(retrofitBase)
        Thread{ getRequest.application(id, json)}.start()
        while(!getRequest.isGet){ /*process status*/ }
        return getRequest
    }
}