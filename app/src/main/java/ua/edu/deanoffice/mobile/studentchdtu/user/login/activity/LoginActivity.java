package ua.edu.deanoffice.mobile.studentchdtu.user.login.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.onesignal.OneSignal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.Utils;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.model.Credentials;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.model.JWToken;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.service.LoginRequests;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity.MainMenuActivity;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.controller.OptionsController;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.MainOptions;

public class LoginActivity extends AppCompatActivity {
    private static final String ONESIGNAL_APP_ID = "10e1ad0d-388b-4205-a9c1-662eec0e4d91";
    boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        App.getInstance().setJwt(null);
        App.getInstance().setCurrentStudent(null);

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.NONE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        OptionsController optionsController = new OptionsController(this);
        MainOptions mainOptions = optionsController.load();
        boolean disablePush = !mainOptions.isEnableNotifOnRegSelectiveCourses();
        OneSignal.disablePush(disablePush);

        Button loginButton = findViewById(R.id.buttonLogin);
        EditText textLogin = findViewById(R.id.textFieldLogin);
        EditText textPassword = findViewById(R.id.textFieldPassword);
        TextView errorText = findViewById(R.id.errorText);
        ImageView showPassword = findViewById(R.id.showPassword);

        showPassword.setOnClickListener((view) -> {
            if (isVisible) {
                textPassword.setTransformationMethod(new PasswordTransformationMethod());
            } else {
                textPassword.setTransformationMethod(null);
            }
            isVisible = !isVisible;
            textPassword.setSelection(textPassword.getText().length());
        });

        loginButton.setOnClickListener((view) -> {
            String login = textLogin.getText().toString();
            String password = textPassword.getText().toString();

            if (!Utils.isStringValid(login) && !Utils.isStringValid(password)) {
                errorText.setText("Виникла помилка, перевірьте правильність введених даних");
                return;
            }

            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Завантаження");
            progressDialog.setProgressStyle(R.style.ProgressBar);
            progressDialog.show();

            App.getInstance().getClient().createRequest(LoginRequests.class)
                    .requestAuthStudent(new Credentials(login, password)).enqueue(new Callback<JWToken>() {
                @Override
                public void onResponse(Call<JWToken> call, Response<JWToken> response) {
                    if (response.isSuccessful()) {
                        LoginActivity.this.onResponse(response.body());
                    } else {
                        errorText.setText("Виникла помилка, перевірте правильність введених даних" + "(" + response.code() + ")");
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<JWToken> call, Throwable t) {
                    errorText.setText("Виникли проблеми з мережею, перевірьте підключення до інтернету.");
                    progressDialog.dismiss();
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
