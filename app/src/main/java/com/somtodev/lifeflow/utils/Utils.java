package com.somtodev.lifeflow.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


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
}

