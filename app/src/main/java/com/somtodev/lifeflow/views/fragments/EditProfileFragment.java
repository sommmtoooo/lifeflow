package com.somtodev.lifeflow.views.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.somtodev.lifeflow.R;
import com.somtodev.lifeflow.lib.FirebaseUtils;

import java.util.HashMap;
import java.util.Map;

public class EditProfileFragment extends Fragment {

    private FirebaseUtils firebaseUtils;

    String bloodGroup, errorMessage;
    EditText edtFirstname, edtLastname;
    TextView tvError;
    Spinner spBloodGroup;
    Button btnUpdate;

    public EditProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseUtils = new FirebaseUtils();

        edtFirstname = (EditText) view.findViewById(R.id.edtFirstname);
        edtLastname = (EditText) view.findViewById(R.id.edtLastname);
        tvError = (TextView) view.findViewById(R.id.tvError);
        spBloodGroup = (Spinner) view.findViewById(R.id.spBloodGroup);

        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);

        updateUI();

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

    private void updateUI() {
        firebaseUtils.database.collection("users").document(firebaseUtils.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    edtFirstname.setText(documentSnapshot.getString("firstname"));
                    edtLastname.setText(documentSnapshot.getString("lastname"));
                    spBloodGroup.setSelection(((ArrayAdapter<String>)spBloodGroup.getAdapter()).getPosition(documentSnapshot.getString("bloodGroup")));
                }
            }
        });
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


        String user_id = firebaseUtils.getCurrentUser().getUid();


        FirebaseUser firebaseUser = firebaseUtils.getCurrentUser();
        DocumentReference documentReference = firebaseUtils.database.collection("users").document(user_id);

        Map<String, String> user = new HashMap<>();
        user.put("firstname", firstname);
        user.put("lastname", lastname);
        user.put("bloodGroup", bloodGroup);

        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void showErrorMessage(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

}