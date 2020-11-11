package ua.edu.deanoffice.mobile.studentchdtu.user.login.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

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

    boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        App.getInstance().setJwt(null);

        Button button = findViewById(R.id.buttonLogin);
        EditText textLogin = findViewById(R.id.textFieldLogin);
        EditText textPassword = findViewById(R.id.textFieldPassword);
        TextView errorText = findViewById(R.id.errorText);
        ImageView showPassword = findViewById(R.id.showPassword);

        showPassword.setOnClickListener((view) -> {
            if(isVisible) {
                textPassword.setTransformationMethod(new PasswordTransformationMethod());
            }else {
                textPassword.setTransformationMethod(null);
            }
            textPassword.setSelection(textPassword.getText().length());
            isVisible = !isVisible;
        });

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
                    }else {
                        errorText.setText("Виникла помилка, перевірьте правильність введених даних" + "(" + response.code() +")");
                    }
                }

                @Override
                public void onFailure(Call<JWToken> call, Throwable t) {
                    errorText.setText("Виникли проблеми з мережею, перевірьте підключення до інтернету.");
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
