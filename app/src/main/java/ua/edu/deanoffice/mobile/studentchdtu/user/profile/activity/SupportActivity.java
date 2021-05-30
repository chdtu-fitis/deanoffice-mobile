package ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.BaseDrawerActivity;

public class SupportActivity extends BaseDrawerActivity {
    private final Map<String, TextView> supportViews = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Load xml for activity
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_support, mainContentBlock, false);
        mainContentBlock.addView(contentView, 1);

        getSupportActionBar().setTitle(getRString(R.string.action_bar_title_support));

        supportViews.put("Email", findViewById(R.id.email));

        String supportEmailString = getRString(R.string.support_email);
        supportViews.get("Email").setText(supportEmailString);

        View buttonSendToSupport = findViewById(R.id.sendToSupport);
        buttonSendToSupport.setOnClickListener(this::onClickSupportEmail);
    }

    private void onClickSupportEmail(View v) {
        String supportEmail = getRString(R.string.support_email);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{supportEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Проблеми з додатком " + getRString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, ""));
    }
}