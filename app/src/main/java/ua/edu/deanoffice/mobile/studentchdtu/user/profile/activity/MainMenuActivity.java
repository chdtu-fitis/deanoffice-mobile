package ua.edu.deanoffice.mobile.studentchdtu.user.profile.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.core.view.GravityCompat;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.BaseDrawerActivity;
import ua.edu.deanoffice.mobile.studentchdtu.user.profile.fragment.MainMenuFragment;

public class MainMenuActivity extends BaseDrawerActivity {

//    Map<String, TextView> studentInformationViews = new HashMap<>();
    private boolean isDoubleClickBackButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Load xml for activity
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_main_menu, mainContentBlock, false);
        mainContentBlock.addView(contentView,1);

        getSupportActionBar().setTitle(getRString(R.string.action_bar_title_main_menu));

        //Load fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MainMenuFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //Close drawer menu
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Double click for exit
            if(isDoubleClickBackButton){
                super.onBackPressed();
            }else {
                Toast.makeText(this, getRString(R.string.info_double_back), Toast.LENGTH_SHORT).show();
                isDoubleClickBackButton = true;
                new Handler().postDelayed(()->{
                    isDoubleClickBackButton = false;
                }, 500);
            }
        }
    }
}