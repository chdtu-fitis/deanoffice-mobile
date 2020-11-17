package ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity;

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
import ua.edu.deanoffice.mobile.studentchdtu.applications.activity.ChooseApplicationActivity;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity.SelectiveCoursesActivity;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.activity.LoginActivity;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.Student;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.service.ProfileRequests;

public class MainMenuActivity extends AppCompatActivity {

    Map<String, TextView> studentInformationViews = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        studentInformationViews.put("Name", (TextView) findViewById(R.id.studentNameTextView));
        studentInformationViews.put("Facult", (TextView) findViewById(R.id.facultyNameTextView));
        studentInformationViews.put("Degree", (TextView) findViewById(R.id.degreeNameTextView));
        studentInformationViews.put("Speciality", (TextView) findViewById(R.id.specialityNameTextView));
        studentInformationViews.put("Specialization", (TextView) findViewById(R.id.specializationNameTextView));
        studentInformationViews.put("GroupAndYear", (TextView) findViewById(R.id.groupNameTextView));
        studentInformationViews.put("Termin", (TextView) findViewById(R.id.tuitionFormNameTextView));

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

        App.getInstance().getClient().createRequest(ProfileRequests.class)
                .requestStudentInfo(App.getInstance().getJwt().getToken()).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) {
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
                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updateStudentInfo(Student user) {
        runOnUiThread(() -> {
            studentInformationViews.get("Name").setText(user.getSurname() + " " + user.getName() + " " + user.getPatronimic());
            studentInformationViews.get("Facult").setText("Факультет інформаційних технологій і систем");
            studentInformationViews.get("Degree").setText(user.getDegrees()[0].getSpecialization().getDegree().getName());
            studentInformationViews.get("Speciality").setText(user.getDegrees()[0].getSpecialization().getSpeciality().getName());
            studentInformationViews.get("Specialization").setText(user.getDegrees()[0].getSpecialization().getName());
            studentInformationViews.get("GroupAndYear").setText(user.getDegrees()[0].getStudentGroup().getName());
            studentInformationViews.get("Termin").setText((user.getDegrees()[0].getTuitionForm().equals("FULL_TIME") ? "Денна" : "Заочна")
                    + (user.getDegrees()[0].getTuitionTerm().equals("REGULAR") ? "" : "Скорочена"));
        });
    }
}