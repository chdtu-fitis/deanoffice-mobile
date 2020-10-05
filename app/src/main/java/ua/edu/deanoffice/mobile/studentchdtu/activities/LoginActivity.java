package ua.edu.deanoffice.mobile.studentchdtu.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.Mobile;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.UserData.Credentials;
import ua.edu.deanoffice.mobile.studentchdtu.service.Utils;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.buttonLogin);
        EditText textLogin = findViewById(R.id.textFieldLogin);
        EditText textPassword = findViewById(R.id.textFieldPassword);

        button.setOnClickListener((view)->{
            String login = textLogin.getText().toString().replaceAll("\\."," ");
            String password = textPassword.getText().toString();

            if(!Utils.isStringValid(login) && !Utils.isStringValid(password)) {
                return;
            }

            Mobile.getInstance().getClient().getUser(new Credentials(login, password), (resp)->OnResponse(resp));
        });
    }

    public void OnResponse(String responseBody) {
        Log.d("Test", responseBody);
        Intent intent = new Intent(LoginActivity.this, ChooseApplicationActivity.class);
        startActivity(intent);
        finish();
    }
}
