package ua.edu.deanoffice.mobile.studentchdtu.applications.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.model.Application;
import ua.edu.deanoffice.mobile.studentchdtu.applications.service.ApplicationRequests;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;
import ua.edu.deanoffice.mobile.studentchdtu.Utils;
import ua.edu.deanoffice.mobile.studentchdtu.applications.model.RetakeApplicationData;

public class RetakeApplicationActivity extends AppCompatActivity {

    private final int id = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retake_application);

        final EditText editText = findViewById(R.id.editText);
        String[] exam1 = new String[]{"іспиту", "заліку"};
        final Spinner spinner = findViewById(R.id.spinnerAppExam);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, exam1);
        spinner.setAdapter(adapter);

        Button button = findViewById(R.id.buttonApp);
        button.setOnClickListener((view) -> {
            App.getInstance().getClient().createRequest(ApplicationRequests.class)
                    .requestStudentInfo(Utils.retakeApplicationDataToJSON(new RetakeApplicationData(editText.getText().toString(), (int) spinner.getSelectedItemId())),
                            id, App.getInstance().getJwt().getToken()).enqueue(new Callback<Application>() {
                @Override
                public void onResponse(Call<Application> call, Response<Application> response) {
                    Log.d("Test", response.code() + "");
                    if (response.isSuccessful()) {
                        RetakeApplicationActivity.this.onResponse(response.body());
                    } else if (response.code() == 401) {
                        Utils.showVersionError(RetakeApplicationActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<Application> call, Throwable t) {
                    Snackbar.make(findViewById(android.R.id.content), "Failed connect to server", Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });
        });

    }

    public void onResponse(Application application) {
        Intent intent = new Intent(RetakeApplicationActivity.this, ExamApplicationActivity.class);
        App.getInstance().getCurrentApplication().load(application);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RetakeApplicationActivity.this, ChooseApplicationActivity.class);
        startActivity(intent);
        finish();
    }


}
