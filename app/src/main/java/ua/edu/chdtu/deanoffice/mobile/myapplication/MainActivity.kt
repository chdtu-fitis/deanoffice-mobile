package ua.edu.chdtu.deanoffice.mobile.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ArrayAdapter
import android.widget.Spinner
import ua.edu.chdtu.deanoffice.mobile.service.client.Client
import ua.edu.chdtu.deanoffice.mobile.service.client.utils.Utils


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, ApplicationTypesActivity::class.java)
            startActivity(intent)
        }


        val spinner = findViewById(R.id.spinner) as Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ArrayList<String>().toArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter





    }
}


