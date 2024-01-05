package com.somtodev.lifeflow.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.somtodev.lifeflow.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    String bloodGroup, errorMessage;
    EditText edtFirstname, edtLastname, edtEmailAddress, edtPassword, edtConfirmPassword;
    Button btnRegister;
    TextView tvLink, tvError;
    Spinner spBloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        edtFirstname = (EditText) findViewById(R.id.edtFirstname);
        edtLastname = (EditText) findViewById(R.id.edtLastname);
        edtEmailAddress = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        tvLink = (TextView) findViewById(R.id.tvLink);
        tvError = (TextView) findViewById(R.id.tvError);
        spBloodGroup = (Spinner) findViewById(R.id.spBloodGroup);

        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        spBloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodGroup = parent.getItemAtPosition((int) id).toString();
                Toast.makeText(RegisterScreen.this, bloodGroup, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(RegisterScreen.this, "Please Select A Blood Group", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void registerUser() {
        String firstname = edtFirstname.getText().toString();
        String lastname = edtLastname.getText().toString();
        String email = edtEmailAddress.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();
        String result = firstname + lastname + email + password + bloodGroup;

        if (TextUtils.isEmpty(firstname) || firstname.length() < 3) {
            showErrorMessage("Provide a firstname");
            return;
        }

        if (TextUtils.isEmpty(lastname) || lastname.length() < 3) {
            showErrorMessage("Provide a lastname");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            showErrorMessage("Provide an email");
            return;
        }

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || password.length() < 6 || confirmPassword.length() < 6) {
            showErrorMessage("Passwords must be 6 characters long");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showErrorMessage("Passwords do not match");
            return;
        }

        tvError.setVisibility(View.GONE);

        Toast.makeText(RegisterScreen.this, result, Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    DocumentReference documentReference = firebaseFirestore.collection("users").document();
                    Map<String, String> user = new HashMap<>();
                    user.put("firstname", firstname);
                    user.put("lastname", lastname);
                    user.put("email", email);
                    user.put("password", password);
                    user.put("bloodGroup", bloodGroup);

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                            startActivity(intent);
                            finish();
                        }

                    });
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthUserCollisionException exception) {
                        showErrorMessage("Account with email exists");
                    } catch (FirebaseAuthWeakPasswordException exception) {
                        showErrorMessage("Password strength is too weak");
                    } catch (Exception exception) {
                        showErrorMessage("An Error Occurred: Please try again");
                        Toast.makeText(RegisterScreen.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showErrorMessage(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }


}