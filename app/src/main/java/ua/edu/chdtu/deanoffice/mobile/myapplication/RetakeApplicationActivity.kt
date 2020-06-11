package ua.edu.chdtu.deanoffice.mobile.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.google.gson.Gson
import ua.edu.chdtu.deanoffice.mobile.service.POJO.RetakeApplicationData
import ua.edu.chdtu.deanoffice.mobile.service.client.Client
import ua.edu.chdtu.deanoffice.mobile.service.client.utils.Utils

class RetakeApplicationActivity : AppCompatActivity() {

    var id: Int = 2;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // finding the edit text
        val editText = findViewById<EditText>(R.id.editText)

        val exam1 = arrayOf("іспиту", "заліку")
        val spinner1 = findViewById(R.id.spinnerAppExam) as Spinner
        val adapter1 = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exam1)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter1

        val button = findViewById<Button>(R.id.buttonApp)
        button.setOnClickListener {
            val intent = Intent(this, ExamApplicationActivity::class.java)
            var temp = Client.getApplication(id, Utils.retakeApplicationDataToJSON(RetakeApplicationData(editText.text.toString(),
                spinner1.selectedItemId.toByte()))).response!!.body
            println(temp);
            ApplicationDataTemp.header = Utils.JSONtoApplication(temp).header
            ApplicationDataTemp.body = "\t\t" + Utils.JSONtoApplication(temp).body
            startActivity(intent)
        }

    }
}