package ua.edu.deanoffice.mobile.studentchdtu.applications.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
import ua.edu.deanoffice.mobile.studentchdtu.applications.model.RenewApplicationData;

public class RenewApplicationActivity extends AppCompatActivity {

    private final int id = 7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_application);

        final EditText textDate = findViewById(R.id.editDate);
        Button buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener((view) -> {
            App.getInstance().getClient().createRequest(ApplicationRequests.class)
                    .requestStudentInfo(Utils.renewApplicationDataToJSON(new RenewApplicationData(textDate.getText().toString())), id,
                            App.getInstance().getJwt().getToken()).enqueue(new Callback<Application>() {
                @Override
                public void onResponse(Call<Application> call, Response<Application> response) {
                    if (response.isSuccessful()) {
                        RenewApplicationActivity.this.onResponse(response.body());
                    } else if (response.code() == 401) {
                        Utils.showVersionError(RenewApplicationActivity.this);
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
        Intent intent = new Intent(RenewApplicationActivity.this, ExamApplicationActivity.class);
        App.getInstance().getCurrentApplication().load(application);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RenewApplicationActivity.this, ChooseApplicationActivity.class);
        startActivity(intent);
        finish();
    }
}
