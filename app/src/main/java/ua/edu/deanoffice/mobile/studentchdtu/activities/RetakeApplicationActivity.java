package ua.edu.deanoffice.mobile.studentchdtu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.Mobile;
import ua.edu.deanoffice.mobile.studentchdtu.service.Utils;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.Client;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.requests.Get;
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
        button.setOnClickListener((view)-> {
            Mobile.getInstance().getClient().getApplication(id, Utils.retakeApplicationDataToJSON(
                    new RetakeApplicationData(editText.getText().toString(), (int) spinner.getSelectedItemId())),
                    new Client.OnResponseCallback() {
                        @Override
                        public void onResponseSuccess(ResponseBody response) {
                            onResponse(response);
                        }

                        @Override
                        public void onResponseFailure(ResponseBody response) {
                            Snackbar.make(findViewById(android.R.id.content), "Failed connect to server", Snackbar.LENGTH_LONG)
                                    .setAction("No action", null).show();
                        }
                    });
        });
    }

    public void onResponse(ResponseBody response){
        try {
            Intent intent = new Intent(RetakeApplicationActivity.this, ExamApplicationActivity.class);
            String body = (String) response.string();
            Mobile.getInstance().getCurrentApplication().load(Utils.JSONtoApplication(body));
            startActivity(intent);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RetakeApplicationActivity.this, ChooseApplicationActivity.class);
        startActivity(intent);
        finish();
    }


}
