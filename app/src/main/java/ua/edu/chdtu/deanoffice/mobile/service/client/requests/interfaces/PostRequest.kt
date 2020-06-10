package ua.edu.chdtu.deanoffice.mobile.service.client.requests.interfaces

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ua.edu.chdtu.deanoffice.mobile.service.POJO.ApplicationTypeIdPOJO

interface PostRequest {
    @POST("make-application")
    fun postRequest(@Body ApplicationTypeId: ApplicationTypeIdPOJO) : Call<ResponseBody>
}