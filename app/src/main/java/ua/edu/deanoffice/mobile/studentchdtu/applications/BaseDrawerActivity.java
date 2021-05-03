package ua.edu.deanoffice.mobile.studentchdtu.applications;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.course.selective.view.activity.SelectiveCoursesActivity;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.activity.LoginActivity;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity.MainOptionsActivity;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.Student;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.service.ProfileRequests;

public abstract class BaseDrawerActivity extends AppCompatActivity {

    protected DrawerLayout drawer;
    protected Toolbar toolbar;
    protected ViewGroup mainContentBlock;
    protected ProgressDialog progressDialog = null;
    private NavigationView navigationView;

    private static int selectedMenuItemId = -1;

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
        navigationView = findViewById(R.id.nav_view);

        showLoadingProgress();

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
                hideLoadingProgress();
            }

            @Override
            public void onFailure(@NonNull Call<Student> call, @NonNull Throwable t) {
                hideLoadingProgress();
                Intent intent = new Intent(BaseDrawerActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            if (selectedMenuItemId == item.getItemId()) return false;

            selectedMenuItemId = item.getItemId();

            switch (selectedMenuItemId) {
                case R.id.nav_selectivecourses:
                    Intent selectiveCoursesActivity = new Intent(this, SelectiveCoursesActivity.class);
                    startActivity(selectiveCoursesActivity);
                    break;
                case R.id.nav_options:
                    Intent mainOptionsActivity = new Intent(this, MainOptionsActivity.class);
                    startActivity(mainOptionsActivity);
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

    public static void setSelectedMenuItemId(int selectedMenuItemId) {
        BaseDrawerActivity.selectedMenuItemId = selectedMenuItemId;
    }

    public void showLoadingProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Завантаження");
            progressDialog.setProgressStyle(R.style.ProgressBar);
            progressDialog.show();
        }
    }

    public void hideLoadingProgress() {
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
            selectedMenuItemId = -1;
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (selectedMenuItemId != -1) {
            navigationView.setCheckedItem(selectedMenuItemId);
        } else {
            int size = navigationView.getMenu().size();
            for (int i = 0; i < size; i++) {
                navigationView.getMenu().getItem(i).setChecked(false);
            }
        }
    }

    protected String getRString(int resource) {
        return getResources().getString(resource);
    }

    public void showError(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_error_message, viewGroup, false);

        TextView titleText = dialogView.findViewById(R.id.errorHeadline);
        TextView bodyText = dialogView.findViewById(R.id.errorBody);

        titleText.setText(getRString(R.string.error_msg_title));
        bodyText.setText(msg);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dialogView.findViewById(R.id.buttonOk).setOnClickListener((viewOk) -> {
            alertDialog.dismiss();
        });
    }

    public void showError(String msg, ErrorDialogListener action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_error_message, viewGroup, false);

        TextView titleText = dialogView.findViewById(R.id.errorHeadline);
        TextView bodyText = dialogView.findViewById(R.id.errorBody);

        titleText.setText(getRString(R.string.error_msg_title));
        bodyText.setText(msg);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dialogView.findViewById(R.id.buttonOk).setOnClickListener((viewOk) -> {
            alertDialog.dismiss();
            action.onClickActionButton();
        });
    }

    public String getServerErrorMessage(Response response){
        String errorMessage = getRString(R.string.error_connection_failed);
        if (response.errorBody() != null) {
            try {
                JSONObject object = new JSONObject(response.errorBody().string());
                errorMessage = object.getString("message");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return errorMessage;
    }

    public interface ErrorDialogListener{
        void onClickActionButton();
    }
}
