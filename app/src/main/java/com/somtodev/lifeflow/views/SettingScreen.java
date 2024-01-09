package com.somtodev.lifeflow.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.somtodev.lifeflow.R;
import com.somtodev.lifeflow.utils.Utils;
import com.somtodev.lifeflow.views.fragments.EditProfileFragment;
import com.somtodev.lifeflow.views.fragments.SettingFragment;

public class SettingScreen extends AppCompatActivity {

    TextView tvScreenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_screen);

        tvScreenName = (TextView) findViewById(R.id.tvScreenName);

        EditProfileFragment editProfileFragment = new EditProfileFragment();
        Utils.setCurrentFragment(this, R.id.flSetting, editProfileFragment);
        setScreeName("Edit Profile");

    }

    private void setScreeName(String pageName) {
        tvScreenName.setText(pageName);
    }

}