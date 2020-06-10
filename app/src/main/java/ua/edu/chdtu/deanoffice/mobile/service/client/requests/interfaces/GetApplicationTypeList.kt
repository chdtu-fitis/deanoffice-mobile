package ua.edu.chdtu.deanoffice.mobile.service.client.requests.interfaces

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface GetApplicationTypeList {
    @GET("application-type")
    fun getApplicationTypeList(): Call<ResponseBody>
}