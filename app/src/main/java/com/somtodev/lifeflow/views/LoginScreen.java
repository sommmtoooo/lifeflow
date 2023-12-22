package com.somtodev.lifeflow.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.somtodev.lifeflow.R;

public class LoginScreen extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    TextView textViewLink, textViewError;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        editTextEmail = (EditText) findViewById(R.id.edtEmail);
        editTextPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        textViewLink = (TextView) findViewById(R.id.tvLink);

        initUIComponents();

    }

    private void initUIComponents() {
        textViewLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginScreen.this, "Sign-in :)", Toast.LENGTH_SHORT).show();
            }

        });

    }
}