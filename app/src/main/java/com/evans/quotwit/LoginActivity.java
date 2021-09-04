package com.evans.quotwit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private static final int PASSWORD_LENGTH = 6;
    EditText mUsername;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewInitializations();
    }

    void viewInitializations(){
        mUsername= findViewById(R.id.et_username);
        mPassword= findViewById(R.id.et_password);
    }

    boolean validateInput() {

        if (mUsername.getText().toString().isEmpty()){
            mUsername.setError("Enter Username");
            return false;
        }
        if (mUsername.getText().toString().equals("")){
            mUsername.setError("Enter Username");
            return false;
        }
        if(mPassword.getText().toString().isEmpty() || mPassword.getText().toString().length()< PASSWORD_LENGTH){
            mPassword.setError("Enter valid password");
            return false;
        }
        return true;
    }

    public void viewTopics (View view) {
        if (validateInput()) {

            // Input is valid, here send data to your server

            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();

            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();

            Intent topics = new Intent(LoginActivity.this, Topics.class);
            topics.putExtra("username", username);

            startActivity(topics);

        }
    }
}