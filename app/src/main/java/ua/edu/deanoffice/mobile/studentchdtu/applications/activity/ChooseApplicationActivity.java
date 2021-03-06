package ua.edu.deanoffice.mobile.studentchdtu.applications.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.model.ApplicationTypePOJO;

public class ChooseApplicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseapplication);

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

        button.setOnClickListener((v) -> {
            Intent intent = new Intent(ChooseApplicationActivity.this, RetakeApplicationActivity.class);

            switch (1 + (int) spinner.getSelectedItemId()) {
                case 1:
                    intent = new Intent(ChooseApplicationActivity.this, RetakeApplicationActivity.class);
                    break;
                case 2:
                    intent = new Intent(ChooseApplicationActivity.this, RenewApplicationActivity.class);
                    break;
                case 3:
                    intent = new Intent(ChooseApplicationActivity.this, DeducApplicationActivity.class);
                    break;
                default:
                    break;
            }
            ChooseApplicationActivity.this.startActivity(intent);
            finish();
        });
    }


}
