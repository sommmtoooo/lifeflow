package com.somtodev.lifeflow.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.somtodev.lifeflow.R;
import com.somtodev.lifeflow.lib.FirebaseUtils;
import com.somtodev.lifeflow.utils.Utils;
import com.somtodev.lifeflow.views.fragments.HomeFragment;
import com.somtodev.lifeflow.views.fragments.RequestFragment;
import com.somtodev.lifeflow.views.fragments.SettingFragment;

public class HomeScreen extends AppCompatActivity {

    private FirebaseUtils firebaseUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        HomeFragment homeFragment = new HomeFragment();
        RequestFragment requestFragment = new RequestFragment();
        SettingFragment settingFragment = new SettingFragment();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        firebaseUtils = new FirebaseUtils();
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
                    case R.id.mtSetting:
                        setCurrentFragment(settingFragment);
                        return true;
                }
                return false;
            }
        });


    }

    public void updateRequestCount() {
        firebaseUtils.database.collection("requests").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
                if (task.isSuccessful()) {
                    int count = 0;
                    for (DocumentSnapshot document : task.getResult()) {
                        count++;
                    }
                    bottomNavigationView.getOrCreateBadge(R.id.mtRequests).setNumber(count);
                }
            }
        });
    }

    public void setCurrentFragment(Fragment fragment) {
        Utils.setCurrentFragment(this, R.id.flDisplay, fragment);
    }

}