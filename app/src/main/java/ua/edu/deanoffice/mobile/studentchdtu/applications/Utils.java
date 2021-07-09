package ua.edu.deanoffice.mobile.studentchdtu.applications;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;

import ua.edu.deanoffice.mobile.studentchdtu.R;
import ua.edu.deanoffice.mobile.studentchdtu.applications.model.RenewApplicationData;
import ua.edu.deanoffice.mobile.studentchdtu.applications.model.RetakeApplicationData;
import ua.edu.deanoffice.mobile.studentchdtu.user.login.activity.LoginActivity;

public class Utils {

    private final static char[] notValidChars = {'=', '/', ' ', '-', '+'};

    public static boolean isStringValid(String string) {
        if (string.isEmpty()) {
            return false;
        }

        for (char ch : notValidChars) {
            if (string.contains("" + ch)) {
                return false;
            }
        }
        return true;
    }

    public static String retakeApplicationDataToJSON(RetakeApplicationData retakeApplicationData) {
        return new Gson().toJson(retakeApplicationData);
    }

    public static String renewApplicationDataToJSON(RenewApplicationData renewApplicationData) {
        return new Gson().toJson(renewApplicationData);
    }

    public static void showVersionError(Activity activity) {
        if(activity == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity)
                .inflate(R.layout.dialog_error_message, viewGroup, false);

        TextView titleText = dialogView.findViewById(R.id.errorHeadline);
        TextView bodyText = dialogView.findViewById(R.id.errorBody);

        Resources resources = activity.getResources();
        titleText.setText(resources.getString(R.string.error_version_outdated_title));
        bodyText.setText(resources.getString(R.string.error_version_outdated_body));
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        buttonOk.setText(resources.getString(R.string.button_update_app));
        buttonOk.setOnClickListener((viewOk) -> {
            if(!activity.getClass().getName().equals(LoginActivity.class.getName())){
                Intent loginActivityIntent = new Intent(activity, LoginActivity.class);
                activity.startActivity(loginActivityIntent);
            }
            final String appPackageName = activity.getPackageName();
            try {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
            alertDialog.dismiss();
        });
    }
}
