package com.evans.quotwit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mTextView2;
    private Button mLoginbtn;
    private Button mSignupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginbtn = findViewById(R.id.loginbtn);
        mSignupbtn = findViewById(R.id.signupbtn);
        mTextView2 = findViewById(R.id.textView2);

        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "log in", Toast.LENGTH_LONG).show();
            }
        });

        mSignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "new user", Toast.LENGTH_LONG).show();
                Intent signup = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signup);
            }
        });

        mTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "skip", Toast.LENGTH_SHORT).show();
            }
        });
    }
}