package ua.edu.chdtu.deanoffice.mobile.service.client.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ua.edu.chdtu.deanoffice.mobile.service.DTO.ApplicationTypeDTO
import ua.edu.chdtu.deanoffice.mobile.service.DTO.objectDTO
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.KClass

class Utils {

    fun  JSONtoArrayObjects(JSON: String) : ArrayList<ApplicationTypeDTO>{
        var obj = ArrayList<ApplicationTypeDTO>();
        obj.addAll(Gson().fromJson(JSON, Array<ApplicationTypeDTO>::class.java))
        return obj
    }

    /*fun  <T>JSONtoObject(JSON: String) : T{
        var obj: T = Gson().fromJson(JSON, object: TypeToken<T>(){}.type);
        return obj
    }*/

    fun objectToJSON(obj: objectDTO) : String{
        return Gson().toJson(obj)
    }

}