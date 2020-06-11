package ua.edu.chdtu.deanoffice.mobile.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ua.edu.chdtu.deanoffice.mobile.service.POJO.ApplicationTypePOJO
import ua.edu.chdtu.deanoffice.mobile.service.POJO.RenewApplicationData
import ua.edu.chdtu.deanoffice.mobile.service.POJO.RetakeApplicationData
import ua.edu.chdtu.deanoffice.mobile.service.client.Client
import ua.edu.chdtu.deanoffice.mobile.service.client.utils.Utils

class DeducApplicationActivity : AppCompatActivity() {

    var id: Int = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deduc_application_activity)

        var textDate = findViewById<EditText>(R.id.editDate1) as EditText
        var buttonNext = findViewById<Button>(R.id.buttonNext) as Button

        textDate.addTextChangedListener(object : TextWatcher{
            var sb = StringBuilder("")
            var ignore = false;
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(ignore){
                    ignore = false
                    return;
                }
                sb.clear()
                sb.append(if(s!!.length > 10){ s.subSequence(0,10) }else{ s })
                if(sb.lastIndex == 2){
                    if(sb[2] != '.'){
                        sb.insert(2,".")
                    }
                } else if(sb.lastIndex == 5){
                    if(sb[5] != '.'){
                        sb.insert(5,".")
                    }
                }
                ignore = true
                textDate.setText(sb.toString())
                textDate.setSelection(sb.length)
            }
        })

        buttonNext.setOnClickListener {
            val intent = Intent(this, ExamApplicationActivity::class.java)
            var temp = Client.getApplication(id, Utils.renewApplicationDataToJSON(RenewApplicationData(textDate.text.toString()))).response!!.body
            println(temp);
            ApplicationDataTemp.header = Utils.JSONtoApplication(temp).header
            ApplicationDataTemp.body = "\t\t" + Utils.JSONtoApplication(temp).body
            startActivity(intent)
        }
    }
}