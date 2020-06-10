package ua.edu.chdtu.deanoffice.mobile.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ArrayAdapter
import android.widget.Spinner
import ua.edu.chdtu.deanoffice.mobile.service.client.utils.Utils
import ua.edu.chdtu.deanoffice.mobile.service.POJO.ApplicationTypePOJO
import ua.edu.chdtu.deanoffice.mobile.service.POJO.RetakeApplicationData
import ua.edu.chdtu.deanoffice.mobile.service.client.Client
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        
        var applicationTypeList = ArrayList<ApplicationTypePOJO>()
        try{
            //applicationTypeList = Utils.JSONtoArrayObjects(Client.getApplicationList().response!!.body)
        }catch (e: Exception){
            println(e.toString())
        }

        //Client.getApplication(0, Utils.retakeApplicationDataToJSON(RetakeApplicationData("Mathematics", 2)) )
        //println(Utils.retakeApplicationDataToJSON(RetakeApplicationData("Mathematics", 2)))

        val spinner = findViewById(R.id.spinner) as Spinner
        var temp = arrayListOf<String>("1","2","3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, temp.toArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        button.setOnClickListener {
            val intent = Intent(this, ApplicationTypesActivity::class.java)
            startActivity(intent)
            
            println(spinner.selectedItemId + 1)
        }



    }
}


