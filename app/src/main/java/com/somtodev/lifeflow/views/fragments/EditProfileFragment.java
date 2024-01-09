package com.somtodev.lifeflow.views.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.somtodev.lifeflow.R;
import com.somtodev.lifeflow.models.User;

public class EditProfileFragment extends Fragment {

    String bloodGroup, errorMessage;
    EditText edtFirstname, edtLastname;
    TextView tvError;
    Spinner spBloodGroup;
    Button btnUpdate;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtFirstname = (EditText) view.findViewById(R.id.edtFirstname);
        edtLastname = (EditText) view.findViewById(R.id.edtFirstname);
        tvError = (TextView) view.findViewById(R.id.tvError);
        spBloodGroup = (Spinner) view.findViewById(R.id.spBloodGroup);

        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);

        spBloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodGroup = parent.getItemAtPosition((int) id).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showErrorMessage("Provide a blood group");
            }
        });

        btnUpdate.setOnClickListener(v -> updateUserProfile());

    }

    private void updateUserProfile() {
        String firstname = edtFirstname.getText().toString();
        String lastname = edtLastname.getText().toString();
        String bloodGroup = spBloodGroup.getSelectedItem().toString();


        if (TextUtils.isEmpty(firstname) || firstname.length() < 3) {
            showErrorMessage("Provide a firstname");
            return;
        }

        if (TextUtils.isEmpty(lastname) || lastname.length() < 3) {
            showErrorMessage("Provide a lastname");
            return;
        }

        if (TextUtils.isEmpty(bloodGroup)) {
            showErrorMessage("Provide a blood group");
            return;
        }

        tvError.setVisibility(View.GONE);


    }

    private void showErrorMessage(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

}