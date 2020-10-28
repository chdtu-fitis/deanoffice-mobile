package ua.edu.deanoffice.mobile.studentchdtu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.RenewApplicationData;

public class RenewApplicationActivity extends AppCompatActivity {

    private int id = 7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_application);

        final EditText textDate = findViewById(R.id.editDate);
        Button buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener((view) -> {
            Mobile.getInstance().getClient().getApplication(id,
                    Utils.renewApplicationDataToJSON(new RenewApplicationData(textDate.getText().toString())),
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
            Intent intent = new Intent(RenewApplicationActivity.this, ExamApplicationActivity.class);
            Mobile.getInstance().getCurrentApplication().load(Utils.JSONtoApplication(response.string()));
            startActivity(intent);
        }catch(IOException e){

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RenewApplicationActivity.this, ChooseApplicationActivity.class);
        startActivity(intent);
        finish();
    }
}
