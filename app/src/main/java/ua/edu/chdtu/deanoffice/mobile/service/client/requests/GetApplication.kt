package ua.edu.chdtu.deanoffice.mobile.service.client.requests

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetApplication {
    @GET("application/param")
    fun getRequest(@Query("ApplicationType") id: Int, @Query("json") json: String): Call<ResponseBody>
}