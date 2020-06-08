package ua.edu.chdtu.deanoffice.mobile.service.client.requests

import retrofit2.Retrofit
import ua.edu.chdtu.deanoffice.mobile.service.POJO.ApplicationTypeIdPOJO

class Post(private var retrofit: Retrofit) {

    var response: Response ?= null;
    var isGet: Boolean = false;

    public fun run(ApplicationTypeId: ApplicationTypeIdPOJO){
        var req = retrofit.create(PostRequest::class.java)
        var resp = req.postRequest(ApplicationTypeId).execute()
        response = Response(resp.body()!!.string())
        isGet = true;
    }
}