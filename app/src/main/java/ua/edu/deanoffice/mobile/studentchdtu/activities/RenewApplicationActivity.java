package ua.edu.deanoffice.mobile.studentchdtu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ua.edu.deanoffice.mobile.studentchdtu.mobile.ApplicationCache;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.Mobile;
import ua.edu.deanoffice.mobile.studentchdtu.service.Utils;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.requests.Get;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.RenewApplicationData;

public class RenewApplicationActivity extends AppCompatActivity {

    int id = 7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_application);

        final EditText textDate = findViewById(R.id.editDate);
        Button buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener((view)->{
            Mobile.getInstance().client.getApplication(id,
                    Utils.renewApplicationDataToJSON(new RenewApplicationData(textDate.getText().toString())),
                    (get)->onResponse(get));
        });
    }

    public void onResponse(Get get){
        Intent intent = new Intent(RenewApplicationActivity.this, ExamApplicationActivity.class);
        String body = get.getResponseBody();
        Mobile.getInstance().currentApplication.load(Utils.JSONtoApplication(body));
        startActivity(intent);
    }
}
