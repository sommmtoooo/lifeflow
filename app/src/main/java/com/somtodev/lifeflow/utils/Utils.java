package com.somtodev.lifeflow.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Utils {
    public static void setCurrentFragment(AppCompatActivity activity, int display, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().replace(display, fragment).commit();
    }

    public static boolean validateName(String data) {
        return true;
    }

    public static boolean validateEmail(String data) {
        return true;
    }

    public static boolean validatePassword(String data) {
        return true;
    }

    public static String getFineFormattedDate(String raw) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate;

        try {
            Date date = inputFormat.parse(raw);
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE MMMM d, yyyy", Locale.ENGLISH);
            assert date != null;
            formattedDate = outputFormat.format(date);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return raw;
        }

    }


    public static String getRawDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date()).toString();
    }
}

