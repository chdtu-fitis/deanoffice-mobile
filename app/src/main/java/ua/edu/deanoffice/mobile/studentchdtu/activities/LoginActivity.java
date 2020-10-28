package ua.edu.deanoffice.mobile.studentchdtu.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.Mobile;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.UserData.Credentials;
import ua.edu.deanoffice.mobile.studentchdtu.service.Utils;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.Client;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.JWToken;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.buttonLogin);
        EditText textLogin = findViewById(R.id.textFieldLogin);
        EditText textPassword = findViewById(R.id.textFieldPassword);

        button.setOnClickListener((view)->{
            String login = textLogin.getText().toString();
            String password = textPassword.getText().toString().replaceAll("\\."," ");

            if(!Utils.isStringValid(login) && !Utils.isStringValid(password)) {
                return;
            }

            Mobile.getInstance().getClient().getUser(new Credentials(login, password), new Client.OnResponseCallback() {
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

    public void onResponse(ResponseBody response) {
        try {
            Mobile.getInstance().jwt = new Gson().fromJson(response.string(), JWToken.class);
            if (Mobile.getInstance().jwt.isValid()) {
                Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
