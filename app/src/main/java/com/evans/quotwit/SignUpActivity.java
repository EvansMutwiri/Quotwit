package com.evans.quotwit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mEmail;
    EditText mPassword;
    EditText mRepeatPassword;

    final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        viewInitializations();
    }

    void viewInitializations(){
        mUsername = findViewById(R.id.et_user_name);
        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mRepeatPassword = findViewById(R.id.et_repeat_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}