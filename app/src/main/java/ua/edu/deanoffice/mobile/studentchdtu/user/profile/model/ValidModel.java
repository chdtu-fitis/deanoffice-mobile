package ua.edu.deanoffice.mobile.studentchdtu.user.profile.model;

import android.util.Log;

import androidx.annotation.Keep;

import java.lang.reflect.Field;

@Keep
public abstract class ValidModel {
    public boolean isValid() {
        try {
            for (Field f : getClass().getDeclaredFields()) {
                if (f.get(this) == null) {
                    Log.w("Debug", "Field " + f.getName() + " is null");
                    return false;
                }
            }
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}
