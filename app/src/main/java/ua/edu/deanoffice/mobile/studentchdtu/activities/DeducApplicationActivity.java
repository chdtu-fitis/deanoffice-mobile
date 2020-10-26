package ua.edu.deanoffice.mobile.studentchdtu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.Mobile;
import ua.edu.deanoffice.mobile.studentchdtu.service.Utils;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.Client;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.requests.Get;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.RenewApplicationData;

public class DeducApplicationActivity extends AppCompatActivity {

    private int id = 8;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deduc_application_activity);

        EditText textDate = findViewById(R.id.editDate1);
        Button buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener((view)->{
            Mobile.getInstance().getClient().getApplication(id,
                    Utils.renewApplicationDataToJSON(new RenewApplicationData(textDate.getText().toString())),
                    new Client.OnResponseCallback() {
                        @Override
                        public void OnResponseSuccess(Response response) {
                            onResponse(response);
                        }

                        @Override
                        public void OnFailureSuccess(Response response) {
                            Snackbar.make(findViewById(android.R.id.content), "Failed connect to server", Snackbar.LENGTH_LONG)
                                    .setAction("No action", null).show();
                        }
                    });
        });
    }

    public void onResponse(Response response){
        Intent intent = new Intent(DeducApplicationActivity.this, ExamApplicationActivity.class);
        String body = (String)response.body();
        Mobile.getInstance().getCurrentApplication().load(Utils.JSONtoApplication(body));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DeducApplicationActivity.this, ChooseApplicationActivity.class);
        startActivity(intent);
        finish();
    }
}
