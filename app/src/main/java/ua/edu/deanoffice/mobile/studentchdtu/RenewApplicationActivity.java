package ua.edu.deanoffice.mobile.studentchdtu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ua.edu.deanoffice.mobile.studentchdtu.service.Utils;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.Client;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.RenewApplicationData;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.RetakeApplicationData;

public class RenewApplicationActivity extends AppCompatActivity {

    int id = 7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_application);

        final EditText textDate = findViewById(R.id.editDate);
        Button buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RenewApplicationActivity.this, ExamApplicationActivity.class);
                String temp = Client.getInstance().getApplication(id, Utils.renewApplicationDataToJSON(new RenewApplicationData(textDate.getText().toString()))).response;
                ApplicationDataTemp.getInstance().header = Utils.JSONtoApplication(temp).header;
                ApplicationDataTemp.getInstance().body = "\t\t" + Utils.JSONtoApplication(temp).body;
                startActivity(intent);
            }
        });
    }
}
