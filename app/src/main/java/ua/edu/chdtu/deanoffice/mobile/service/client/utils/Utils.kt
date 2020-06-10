package ua.edu.chdtu.deanoffice.mobile.service.client.utils

import com.google.gson.Gson
import ua.edu.chdtu.deanoffice.mobile.service.POJO.Application
import ua.edu.chdtu.deanoffice.mobile.service.POJO.ApplicationTypeIdPOJO
import ua.edu.chdtu.deanoffice.mobile.service.POJO.ApplicationTypePOJO
import ua.edu.chdtu.deanoffice.mobile.service.POJO.RetakeApplicationData
import kotlin.collections.ArrayList

class Utils {

    fun  JSONtoArrayObjects(JSON: String) : ArrayList<ApplicationTypePOJO>{
        var obj = ArrayList<ApplicationTypePOJO>();
        obj.addAll(Gson().fromJson(JSON, Array<ApplicationTypePOJO>::class.java))
        return obj
    }

    fun  JSONtoApplication(JSON: String) : Application {
        var application = Gson().fromJson(JSON, Application::class.java)
        return application
    }

    fun applicationTypeIdPOJOtoJSON(applicationTypeIdPOJO: ApplicationTypeIdPOJO) : String{
        return Gson().toJson(applicationTypeIdPOJO)
    }

    fun retakeApplicationDataToJSON(retakeApplicationData : RetakeApplicationData) : String{
        return Gson().toJson(retakeApplicationData)
    }

    /*fun  <T>JSONtoObject(JSON: String) : T{
        var obj: T = Gson().fromJson(JSON, object: TypeToken<T>(){}.type);
        return obj
    }*/
}