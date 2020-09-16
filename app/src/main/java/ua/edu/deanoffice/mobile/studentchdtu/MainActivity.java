package ua.edu.deanoffice.mobile.studentchdtu;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.ApplicationTypePOJO;
import ua.edu.deanoffice.mobile.studentchdtu.service.pojo.RenewApplicationData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        ArrayList<ApplicationTypePOJO> applicationTypeList = new ArrayList<ApplicationTypePOJO>();

        final Spinner spinner = findViewById(R.id.spinner);
        String[] temp = new String[]{
                "Перескладання іспиту для підвищення оцінки з метою отримання диплому з відзнакою",
                "Поновлення до складу здобувачів вищої освіти після академічної відпустки",
                "Відрахування за власним бажанням"
        };

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.multiline_spinner_dropdown_item, temp);

        spinner.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RetakeApplicationActivity.class);

                switch (1+(int)spinner.getSelectedItemId()){
                    case 1:
                        intent = new Intent(MainActivity.this, RetakeApplicationActivity.class);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, RenewApplicationActivity.class);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, DeducApplicationActivity.class);
                        break;

                        default:
                            break;
                }
                Log.d("Test", "enter");
                MainActivity.this.startActivity(intent);
                finish();
            }
        });
    }



}
