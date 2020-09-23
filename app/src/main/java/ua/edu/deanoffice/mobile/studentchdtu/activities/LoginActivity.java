package ua.edu.deanoffice.mobile.studentchdtu.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ua.edu.deanoffice.mobile.studentchdtu.R;

public class LoginActivity extends AppCompatActivity {
    public static Context context;
    /*public static String getJWT(){
        return context.getSharedPreferences("jwt", Context.MODE_PRIVATE);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        Button button = findViewById(R.id.buttonLogin);

        button.setOnClickListener((view)->{
            Intent intent = new Intent(LoginActivity.this, ChooseApplicationActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
