package ua.edu.chdtu.deanoffice.mobile.myapplication

import android.icu.util.Calendar
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormat
import java.text.SimpleDateFormat

class ExamApplicationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_application)

        var applicationHeader = findViewById<TextView>(R.id.applicationHeader)
        var applicationBody = findViewById<TextView>(R.id.applicationBody)
        var applicationDateTime = findViewById<TextView>(R.id.applicationDateTime)

        applicationHeader.setText(ApplicationDataTemp.header.replace("\\n", "\n"))
        applicationBody.setText(ApplicationDataTemp.body)
        val df: DateFormat = SimpleDateFormat("dd.MM.yyyy")
        val date: String = df.format(Calendar.getInstance().time)
        applicationDateTime.setText(date)
    }
}
