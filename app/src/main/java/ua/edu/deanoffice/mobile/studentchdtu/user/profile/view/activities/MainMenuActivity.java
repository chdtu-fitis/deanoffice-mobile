package ua.edu.deanoffice.mobile.studentchdtu.user.profile.view.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.view.activities.ChooseApplicationActivity;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activities.SelectiveCoursesActivity;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.view.activities.LoginActivity;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.Student;

public class MainMenuActivity extends AppCompatActivity {

    Map<String, TextView> studentInformationViews = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        studentInformationViews.put("Name", (TextView) findViewById(R.id.StudentName));
        studentInformationViews.put("Facult", (TextView) findViewById(R.id.StudentFacult));
        studentInformationViews.put("Program", (TextView) findViewById(R.id.StudentProgram));
        studentInformationViews.put("Specialization", (TextView) findViewById(R.id.StudentSpecialization));
        studentInformationViews.put("GroupAndYear", (TextView) findViewById(R.id.StudentGroupAndYear));
        studentInformationViews.put("Termin", (TextView) findViewById(R.id.StudentTermin));

        ImageButton btnExit = findViewById(R.id.buttonExit);
        btnExit.setOnClickListener((view) -> {
            Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        Button btnSelection = findViewById(R.id.coursesSelecion);
        btnSelection.setOnClickListener((view) -> {
            Intent intent = new Intent(MainMenuActivity.this, SelectiveCoursesActivity.class);
            startActivity(intent);
            finish();
        });

        Button btnApplication = findViewById(R.id.applications);
        btnApplication.setOnClickListener((view) -> {
            Intent intent = new Intent(MainMenuActivity.this, ChooseApplicationActivity.class);
            startActivity(intent);
            finish();
        });

        App.getInstance().getClient().getRequests().requestStudentInfo(App.getInstance().jwt.token).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if(response.isSuccessful()) {
                    Student student = response.body();
                    if (student.isValid()) {
                        App.getInstance().setCurrentStudent(student);
                        updateStudentInfo(App.getInstance().getCurrentStudent());
                    } else {
                        Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), "Failed connect to server", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updateStudentInfo(Student user) {
        runOnUiThread(() -> {
            studentInformationViews.get("Name").setText(user.getSurname() + " " + user.getName() + " " + user.getPatronimic());
            studentInformationViews.get("Facult").setText("Факультет інформаційних технологій і систем");
            studentInformationViews.get("Program").setText("Спеціальність: " + user.getDegrees()[0].getSpecialization().getSpeciality().getName());
            studentInformationViews.get("Specialization").setText("Освітня програма: " + user.getDegrees()[0].getSpecialization().getName());
            studentInformationViews.get("GroupAndYear").setText("Група: " + user.getDegrees()[0].getStudentGroup().getName());
            studentInformationViews.get("Termin").setText("Форма навчання: " + (user.getDegrees()[0].getTuitionForm().equals("FULL_TIME") ? "Денна" : "Заочна")
                    + (user.getDegrees()[0].getTuitionTerm().equals("REGULAR") ? "" : "Скорочена"));
        });
    }
}