package ua.edu.deanoffice.mobile.studentchdtu.user.login.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.model.Credentials;
import ua.edu.deanoffice.mobile.studentchdtu.applications.Utils;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.model.JWToken;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.service.LoginRequests;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity.MainMenuActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        App.getInstance().setJwt(null);

        Button button = findViewById(R.id.buttonLogin);
        EditText textLogin = findViewById(R.id.textFieldLogin);
        EditText textPassword = findViewById(R.id.textFieldPassword);

        button.setOnClickListener((view) -> {
            String login = textLogin.getText().toString();
            String password = textPassword.getText().toString();

            if (!Utils.isStringValid(login) && !Utils.isStringValid(password)) {
                return;
            }

            App.getInstance().getClient().createRequest(LoginRequests.class)
                    .requestAuthStudent(new Credentials(login, password)).enqueue(new Callback<JWToken>() {
                @Override
                public void onResponse(Call<JWToken> call, Response<JWToken> response) {
                    if (response.isSuccessful()) {
                        LoginActivity.this.onResponse(response.body());
                    }
                }

                @Override
                public void onFailure(Call<JWToken> call, Throwable t) {
                    Snackbar.make(findViewById(android.R.id.content), "Failed connect to server", Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });

        });
    }

    public void onResponse(JWToken jwt) {
        App.getInstance().setJwt(jwt);
        if (App.getInstance().getJwt().isValid()) {
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
