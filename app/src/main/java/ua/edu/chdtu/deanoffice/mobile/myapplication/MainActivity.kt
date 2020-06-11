package ua.edu.chdtu.deanoffice.mobile.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ArrayAdapter
import android.widget.Spinner
import ua.edu.chdtu.deanoffice.mobile.service.POJO.ApplicationTypePOJO


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        
        var applicationTypeList = ArrayList<ApplicationTypePOJO>()

        //applicationTypeList = Utils.JSONtoArrayObjects(Client.getApplicationList().response!!.body)
        //Client.getApplication(0, Utils.retakeApplicationDataToJSON(RetakeApplicationData("Mathematics", 2)) )
        //println(Utils.retakeApplicationDataToJSON(RetakeApplicationData("Mathematics", 2)))

        val spinner = findViewById(R.id.spinner) as Spinner
        var temp = arrayListOf<String>(
                "Перескладання іспиту для підвищення оцінки з метою отримання диплому з відзнакою",
                "Поновлення до складу здобувачів вищої освіти після академічної відпустки",
                "Відрахування за власним бажанням")
        val adapter = ArrayAdapter(this, R.layout.multiline_spinner_dropdown_item, temp.toArray())

        spinner.adapter = adapter

        button.setOnClickListener {
            var intent = Intent(this, RetakeApplicationActivity::class.java)

            when(1+spinner.selectedItemId){
                1L -> { intent = Intent(this, RetakeApplicationActivity::class.java) }
                2L -> { intent = Intent(this, RenewApplicationActivity::class.java) }
                3L -> { intent = Intent(this, DeducApplicationActivity::class.java) }
            }

            startActivity(intent)
        }



    }
}


