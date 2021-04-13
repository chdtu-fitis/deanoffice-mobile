package ua.edu.deanoffice.mobile.studentchdtu.applications;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.TestDrawerActivity;
import ua.edu.deanoffice.mobile.studentchdtu.TestDrawerActivity2;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity.SelectiveCoursesActivity;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.activity.LoginActivity;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.Student;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.service.ProfileRequests;

public abstract class BaseDrawerActivity extends AppCompatActivity {

    protected DrawerLayout drawer;
    protected Toolbar toolbar;
    protected ViewGroup mainContentBlock;
    protected ProgressDialog progressDialog = null;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_base_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        drawer = findViewById(R.id.drawer_layout);
        mainContentBlock = findViewById(R.id.mainContentBlock);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);

        showLoadingDialog();

        App.getInstance().getClient().createRequest(ProfileRequests.class)
                .requestStudentInfo(App.getInstance().getJwt().getToken()).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {
                if (response.isSuccessful()) {
                    Student student = response.body();
                    if (student.isValid()) {
                        App.getInstance().setCurrentStudent(student);
                        TextView menuHeader = navigationView.getHeaderView(0).findViewById(R.id.student_name);
                        String studentName = student.getSurname() + " " + student.getName() + " " + student.getPatronimic();
                        menuHeader.setText(studentName);
                    } else {
                        Intent intent = new Intent(BaseDrawerActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                hideLoadingDialog();
            }

            @Override
            public void onFailure(@NonNull Call<Student> call, @NonNull Throwable t) {
                hideLoadingDialog();
                Intent intent = new Intent(BaseDrawerActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_selectivecourses:
                    Intent selectiveCoursesActivity = new Intent(this, SelectiveCoursesActivity.class);
                    startActivity(selectiveCoursesActivity);
                    break;
                case R.id.nav_exitFrom:
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.nav_info:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            new StudentInformationFragment(App.getInstance().getCurrentStudent())).commit();
//                    drawer.closeDrawers();
//                    break;
                case R.id.nav_schedule:
                case R.id.nav_applications:
                default:
                    Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
            }
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            return true;
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    protected void showLoadingDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Завантаження");
            progressDialog.setProgressStyle(R.style.ProgressBar);
            progressDialog.show();
        }
    }

    protected void hideLoadingDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected String getRString(int resource) {
        return getResources().getString(resource);
    }
}
