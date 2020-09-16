package ua.edu.deanoffice.mobile.studentchdtu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.internal.Util;
import ua.edu.deanoffice.mobile.studentchdtu.service.Utils;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.Client;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.RetakeApplicationData;

public class RetakeApplicationActivity extends AppCompatActivity {

    private int id = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final EditText editText = findViewById(R.id.editText);

        String[] exam1 = new String[]{"іспиту", "заліку"};

        final Spinner spinner = findViewById(R.id.spinnerAppExam);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, exam1);

        spinner.setAdapter(adapter);

        Button button = findViewById(R.id.buttonApp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetakeApplicationActivity.this, ExamApplicationActivity.class);
                String temp = Client.getInstance().getApplication(id, Utils.retakeApplicationDataToJSON(
                        new RetakeApplicationData(editText.getText().toString(), (int)spinner.getSelectedItemId()))).response;
                Log.d("Test", temp);

                ApplicationDataTemp.getInstance().header = Utils.JSONtoApplication(temp).header;
                ApplicationDataTemp.getInstance().body = "\t\t" + Utils.JSONtoApplication(temp).body;
                startActivity(intent);
            }
        });
    }
}
