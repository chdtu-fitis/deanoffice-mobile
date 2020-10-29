package ua.edu.deanoffice.mobile.studentchdtu.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.service.model.student.Student;

public class MainMenuActivity extends AppCompatActivity {

    Map<String, TextView> studentInformationViews = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        studentInformationViews.put("Name", (TextView) findViewById(R.id.StudentName));
        studentInformationViews.put("Facult", (TextView) findViewById(R.id.StudentFacult));
        studentInformationViews.put("GroupAndYear", (TextView) findViewById(R.id.StudentGroupAndYear));

        /*Mobile.getInstance().getClient().getUserData(new Client.OnResponseCallback() {
            @Override
            public void onResponseSuccess(ResponseBody response) {
                try {
                    Student student = new Gson().fromJson(response.string(), Student.class);
                    if (student.isValid()) {
                        Mobile.getInstance().setStudent(student);
                        updateStudentInfo(Mobile.getInstance().getStudent());
                    } else {
                        Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onResponseFailure(ResponseBody response) {
                Snackbar.make(findViewById(android.R.id.content), "Failed connect to server", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });*/
    }

    public void updateStudentInfo(Student user) {
        runOnUiThread(() -> {
            studentInformationViews.get("Name").setText(user.getSurname() + " " + user.getName() + " " + user.getPatronimic());
            studentInformationViews.get("Facult").setText(user.getDegrees()[0].getSpecialization().getSpeciality().getName());
            studentInformationViews.get("GroupAndYear").setText(user.getDegrees()[0].getStudentGroup().getName() + " Курс: " + ((int)Math.ceil((double)(user.getYear())/2d)));
        });
    }
}