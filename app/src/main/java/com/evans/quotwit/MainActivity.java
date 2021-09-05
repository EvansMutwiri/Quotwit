package com.evans.quotwit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView) Button mTextView;
    @BindView(R.id.loginbtn) Button mLoginbtn;
    @BindView(R.id.signupbtn) Button mSignupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mSignupbtn.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "new user", Toast.LENGTH_LONG).show();
            Intent signup = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(signup);
        });

        mLoginbtn.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "log in", Toast.LENGTH_LONG).show();
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
        });

        mTextView.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "skip", Toast.LENGTH_SHORT).show();
            Intent topics = new Intent(MainActivity.this, Topics.class);
            startActivity(topics);
        });
    }
}