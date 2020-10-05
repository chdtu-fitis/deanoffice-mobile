package ua.edu.deanoffice.mobile.studentchdtu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ua.edu.deanoffice.mobile.studentchdtu.mobile.ApplicationCache;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.Mobile;

public class ExamApplicationActivity extends AppCompatActivity {

    private int id = 7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_application);
        Log.d("Test", "Enter");

        TextView applicationHeader = findViewById(R.id.applicationHeader);
        TextView applicationBody = findViewById(R.id.applicationBody);
        TextView applicationDateTime = findViewById(R.id.applicationDateTime);

        applicationHeader.setText(Mobile.getInstance().getCurrentApplication().getHeader());
        applicationBody.setText(Mobile.getInstance().getCurrentApplication().getBody());
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        applicationDateTime.setText(date);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ExamApplicationActivity.this, ChooseApplicationActivity.class);
        startActivity(intent);
        finish();
    }
}
