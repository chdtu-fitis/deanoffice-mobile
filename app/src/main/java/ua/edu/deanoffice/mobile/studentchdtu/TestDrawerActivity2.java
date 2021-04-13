package ua.edu.deanoffice.mobile.studentchdtu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import ua.edu.deanoffice.mobile.studentchdtu.applications.BaseDrawerActivity;

public class TestDrawerActivity2 extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.activity_test2, mainContentBlock, false);
        mainContentBlock.addView(contentView,1);
    }
}