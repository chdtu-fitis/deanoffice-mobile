package ua.edu.chdtu.deanoffice.mobile.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import ua.edu.chdtu.deanoffice.mobile.service.client.Client
import ua.edu.chdtu.deanoffice.mobile.service.client.utils.Utils





class ApplicationTypesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        // finding the button
        val showButton = findViewById<Button>(R.id.buttonApp)

        // finding the edit text
        val editText = findViewById<EditText>(R.id.editText)

        showButton.setOnClickListener {

            val text = editText.text

        }

        val exam = arrayOf("бакалаврського", "магістерського")
        val spinner = findViewById(R.id.spinnerApp) as Spinner
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exam)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        val exam1 = arrayOf("іспиту", "заліку")
        val spinner1 = findViewById(R.id.spinnerAppExam) as Spinner
        val adapter1 = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exam1)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter1





    }
}
