package ua.edu.chdtu.deanoffice.mobile.service.client.requests

import retrofit2.Retrofit
import ua.edu.chdtu.deanoffice.mobile.service.client.requests.interfaces.GetApplication
import ua.edu.chdtu.deanoffice.mobile.service.client.requests.interfaces.GetApplicationTypeList

class Get(private var retrofit: Retrofit){

    var response: Response ?= null;
    var isGet: Boolean = false;

    public fun emptyGet(){
        var req = retrofit.create(GetApplicationTypeList::class.java)
        var resp = req.getApplicationTypeList().execute()
        response = Response(resp.body()!!.string())
        isGet = true;
    }

    public fun applicationTypeList(){
        var req = retrofit.create(GetApplicationTypeList::class.java)
        var resp = req.getApplicationTypeList().execute()
        response = Response(resp.body()!!.string())
        isGet = true;
    }

    public fun application(id: Int, json: String){
        var req = retrofit.create(GetApplication::class.java)
        var resp = req.getRequest(id, json).execute()
        response = Response(resp.body()!!.string())
        isGet = true;
    }
}