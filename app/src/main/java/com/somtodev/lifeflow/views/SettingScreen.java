package com.somtodev.lifeflow.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.somtodev.lifeflow.R;
import com.somtodev.lifeflow.utils.Utils;
import com.somtodev.lifeflow.views.fragments.EditProfileFragment;

public class SettingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_screen);

        EditProfileFragment editProfileFragment = new EditProfileFragment();
        Utils.setCurrentFragment(this, R.id.flSetting, editProfileFragment);

    }

}