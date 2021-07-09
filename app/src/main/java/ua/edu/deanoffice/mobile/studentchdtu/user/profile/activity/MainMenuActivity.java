package ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.BaseDrawerActivity;
import ua.edu.deanoffice.mobile.studentchdtu.shared.service.App;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.activity.LoginActivity;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.fragment.MainMenuFragment;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.model.StudentDegree;

public class MainMenuActivity extends BaseDrawerActivity {
    private boolean isDoubleClickBackButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Load xml for activity
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_main_menu, mainContentBlock, false);
        mainContentBlock.addView(contentView, 1);

        getSupportActionBar().setTitle(getRString(R.string.action_bar_title_main_menu));

        //Load fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MainMenuFragment()).commit();
    }

    @Override
    public void onMainMenuItemClick(int menuItemId) {
        super.onMainMenuItemClick(menuItemId);
    }

    @Override
    public boolean isRoleStudent() {
        return super.isRoleStudent();
    }

    @Override
    protected void onGetStudent() {
        AppCompatButton button = findViewById(R.id.openDegreesListButton);
        if (App.getInstance().getCurrentStudent().getDegrees().length > 1) {
            String selectedDegreeName = App.getInstance().getSelectedStudentDegree().getSpecialization().getName();
            String labelString = getRString(R.string.label_button_switching_degrees);
            labelString = labelString.replace("{specialization_name}", selectedDegreeName);
            button.setText(labelString);
            button.setVisibility(View.VISIBLE);
        }
    }

    public void openDegreesSelectorDialog(View view) {
        Dialog dialog = new Dialog(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_degrees_list, null);

        RadioGroup degreesContainer = dialogView.findViewById(R.id.degreesSelectorRadioGroup);
        degreesContainer.removeAllViews();

        int currentSelectedDegreeId = App.getInstance().getSelectedStudentDegree().getId();
        for (StudentDegree studentDegree : App.getInstance().getCurrentStudent().getDegrees()) {
            String specializationName = studentDegree.getSpecialization().getName();
            int degreeId = studentDegree.getId();
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(specializationName);
            radioButton.setTag(degreeId);
            radioButton.setTextAppearance(R.style.RadioButton);

            if (degreeId == currentSelectedDegreeId) {
                radioButton.setChecked(true);
            }

            degreesContainer.addView(radioButton);
        }

        dialog.setContentView(dialogView);

        RadioGroup resourceTypeRadioGroup = dialogView.findViewById(R.id.degreesSelectorRadioGroup);

        resourceTypeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRB = dialogView.findViewById(checkedId);
            int selectedDegreeId = (int) selectedRB.getTag();
            String selectedDegreeName = selectedRB.getText().toString();

            for (StudentDegree studentDegree : App.getInstance().getCurrentStudent().getDegrees()) {
                if (studentDegree.getId() == selectedDegreeId) {
                    App.getInstance().setSelectedStudentDegree(studentDegree);
                }
            }

            dialog.cancel();

            String labelString = getRString(R.string.label_button_switching_degrees);
            labelString = labelString.replace("{specialization_name}", selectedDegreeName);
            AppCompatButton button = findViewById(R.id.openDegreesListButton);
            button.setText(labelString);
        });

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(dialog.getWindow().getAttributes());
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setAttributes(params);

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSelectedMenuItemId(-1);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //Close drawer menu
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Double click for exit
            if (isDoubleClickBackButton) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, getRString(R.string.info_double_back), Toast.LENGTH_SHORT).show();
                isDoubleClickBackButton = true;
                new Handler().postDelayed(() -> {
                    isDoubleClickBackButton = false;
                }, 500);
            }
        }
    }
}