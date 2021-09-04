package com.evans.quotwit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mEmail;
    EditText mPassword;
    EditText mRepeatPassword;

    final int MIN_PASSWORD_LENGTH = 6;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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

    boolean validateInput (){
        if (mUsername.getText().toString().equals("")){
            mUsername.setError("Please Choose Username");
            return false;
        }
        if (mEmail.getText().toString().isEmpty()){
            mEmail.setError("Please Enter Email address");
            return false;
        }
        if (mEmail.getText().toString().trim().matches(emailPattern)){
            Toast.makeText(SignUpActivity.this, "email is valid", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (mPassword.getText().toString().isEmpty()|| mPassword.getText().toString().equals("")){
            mPassword.setError("Enter password");
            return false;
        }
        if (mPassword.getText().toString().length() < MIN_PASSWORD_LENGTH) {
            mPassword.setError("Password length must be min " + MIN_PASSWORD_LENGTH);
            return false;
        }
        if (!mPassword.getText().toString().equals(mRepeatPassword.getText().toString())) {
            mRepeatPassword.setError("Password does not match");
            return false;
        }
        return true;
    }


    public void submitSignup (View view) {
        if (validateInput()) {

            //send data to server

            String username = mUsername.getText().toString();
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            String repeatPassword = mRepeatPassword.getText().toString();

            Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();

            //call API BUT meanwhile ill add intent to login
            Intent login = new Intent(SignUpActivity.this, LoginActivity.class);
            login.putExtra("username", username);
            login.putExtra("password", password);
            startActivity(login);

        }

    }
}