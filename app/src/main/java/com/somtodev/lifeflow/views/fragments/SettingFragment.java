package com.somtodev.lifeflow.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.somtodev.lifeflow.R;
import com.somtodev.lifeflow.views.LoginScreen;
import com.somtodev.lifeflow.views.SettingScreen;

public class SettingFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Activity activity;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Activity activity = getActivity();
        assert activity != null;

        NavigationView navigationView = activity.findViewById(R.id.navProfile);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.mtEdit) {
                    setScreen(R.id.mtEdit);
                } else if (id == R.id.mtIssues) {
                    Toast.makeText(activity, "Feature Not Implemented", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.mtLogOut) {
                    Toast.makeText(activity, "Logging out :)", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            mAuth.signOut();
                            Intent intent = new Intent(view.getContext(), LoginScreen.class);
                            view.getContext().startActivity(intent);
                            getActivity().finish();
                        }
                    }, 1000);
                } else {
                    return false;
                }

                return true;
            }
        });

    }

    private void setScreen(int id) {
        Intent intent = new Intent(getActivity(), SettingScreen.class);
        intent.putExtra("FRAGMENT", id);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_fragment, container, false);
    }
}