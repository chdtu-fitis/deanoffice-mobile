package ua.edu.deanoffice.mobile.studentchdtu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ua.edu.deanoffice.mobile.studentchdtu.ApplicationDataTemp;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.service.Utils;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.Client;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.requests.Get;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.RenewApplicationData;

public class DeducApplicationActivity extends AppCompatActivity {

    int id = 8;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deduc_application_activity);

        final EditText textDate = findViewById(R.id.editDate1);
        Button buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener((view)->{
            Client.getInstance().getApplication(id,
                    Utils.renewApplicationDataToJSON(new RenewApplicationData(textDate.getText().toString())),
                    (get) -> onResponse(get));
        });
    }

    public void onResponse(Get get){
        Intent intent = new Intent(DeducApplicationActivity.this, ExamApplicationActivity.class);
        String body = get.getResponseBody();
        ApplicationDataTemp.getInstance().header = Utils.JSONtoApplication(body).header;
        ApplicationDataTemp.getInstance().body = "\t\t" + Utils.JSONtoApplication(body).body;
        startActivity(intent);
    }
}
