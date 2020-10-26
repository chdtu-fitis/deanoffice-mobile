package ua.edu.deanoffice.mobile.studentchdtu.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.Mobile;
import ua.edu.deanoffice.mobile.studentchdtu.service.client.Client;
import ua.edu.deanoffice.mobile.studentchdtu.service.model.student.Student;

public class MainMenuActivity extends AppCompatActivity {

    TextView[] studentInfo = new TextView[3];
    Map<String, TextView> informationAboutStudent = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        informationAboutStudent.put("Name", (TextView) findViewById(R.id.StudentName));
        informationAboutStudent.put("Facult", (TextView) findViewById(R.id.StudentFacult));
        informationAboutStudent.put("Group", (TextView) findViewById(R.id.StudentGroup));

        Mobile.getInstance().getClient().getUserData(new Client.OnResponseCallback() {
            @Override
            public void OnResponseSuccess(Response response) {
                Student student = new Gson().fromJson((String) response.body(), Student.class);
                if (student.isValid()) {
                    Mobile.getInstance().setStudent(student);
                    updateStudentInfo(Mobile.getInstance().getStudent());
                } else {
                    Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void OnFailureSuccess(Response response) {
                Snackbar.make(findViewById(android.R.id.content), "Failed connect to server", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void updateStudentInfo(Student user) {
        runOnUiThread(() -> {
            informationAboutStudent.get("Name").setText(user.getName() + " " + user.getSurname() + " " + user.getPatronimic());
            informationAboutStudent.get("Facult").setText(user.getDegrees()[0].getSpecialization().getSpeciality().getName());
            informationAboutStudent.get("Group").setText(user.getDegrees()[0].getStudentGroup().getName());
        });
    }
}