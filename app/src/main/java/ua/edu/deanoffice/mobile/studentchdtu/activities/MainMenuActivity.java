package ua.edu.deanoffice.mobile.studentchdtu.activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.Mobile;
import ua.edu.deanoffice.mobile.studentchdtu.mobile.UserData.User;

public class MainMenuActivity extends AppCompatActivity {

    TextView[] studentInfo = new TextView[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        studentInfo[0] = findViewById(R.id.StudentName);
        studentInfo[1] = findViewById(R.id.StudentFacult);
        studentInfo[2] = findViewById(R.id.StudentGroup);

        Mobile.getInstance().getClient().getUserData((resp)->{
            Log.d("Test", resp);
            updateStudentInfo(new Gson().fromJson(resp, User.class));
        });
    }

    public void updateStudentInfo(User user){
        studentInfo[0].setText(user.getName()+ " " + user.getSurname()+ " " + user.getPatronymic());
        studentInfo[1].setText(user.getCourse());
        studentInfo[2].setText(user.getGroup());
    }
}
