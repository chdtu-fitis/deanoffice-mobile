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

        val languages = resources.getStringArray(R.array.Languages)

        // access the spinner
        var client = Client()
        var json = client.emptyGet().response!!.body
        var applicationtypes = Utils().JSONtoArrayObjects(json)
        var applicationTypeNames:ArrayList <String> = ArrayList()
        for (item in applicationtypes) {
         applicationTypeNames.add(item.name)
        }

        val spinner = findViewById(R.id.spinner) as Spinner
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, applicationTypeNames.toArray())
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Применяем адаптер к элементу spinner
        spinner.adapter = adapter



    }
}


