package ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.BaseDrawerActivity;

public class MainOptionsActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Load xml for activity
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_main_options, mainContentBlock, false);
        mainContentBlock.addView(contentView,1);

        getSupportActionBar().setTitle(getRString(R.string.action_bar_title_main_options));

        loadOptions();
    }

    private void loadOptions(){

    }
}