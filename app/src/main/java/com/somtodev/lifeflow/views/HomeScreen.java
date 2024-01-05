package com.somtodev.lifeflow.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.somtodev.lifeflow.R;
import com.somtodev.lifeflow.utils.Utils;
import com.somtodev.lifeflow.views.fragments.HomeFragment;
import com.somtodev.lifeflow.views.fragments.NotificationFragment;
import com.somtodev.lifeflow.views.fragments.RequestFragment;
import com.somtodev.lifeflow.views.fragments.SettingFragment;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        HomeFragment homeFragment = new HomeFragment();
        RequestFragment requestFragment = new RequestFragment();
        NotificationFragment notificationFragment = new NotificationFragment();
        SettingFragment settingFragment = new SettingFragment();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        setCurrentFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mtHome:
                        setCurrentFragment(homeFragment);
                        return true;
                    case R.id.mtRequests:
                        setCurrentFragment(requestFragment);
                        return true;
                    case R.id.mtNotification:
                        setCurrentFragment(notificationFragment);
                        return true;
                    case R.id.mtSetting:
                        setCurrentFragment(settingFragment);
                        return true;
                }
                return false;
            }
        });

        bottomNavigationView.getOrCreateBadge(R.id.mtRequests).setNumber(10);
        bottomNavigationView.getOrCreateBadge(R.id.mtNotification).setNumber(4);

    }

    public void setCurrentFragment(Fragment fragment) {
        Utils.setCurrentFragment(this, R.id.flDisplay, fragment);
    }

}