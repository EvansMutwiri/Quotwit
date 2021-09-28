package com.evans.quotwit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import ui.LoginActivity;
import ui.SignUpActivity;
import ui.Topics;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.textView)
    Button mTextView;
    @BindView(R.id.loginbtn)
    Button mLoginbtn;
    @BindView(R.id.signupbtn)
    Button mSignupbtn;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mSignupbtn.setOnClickListener(this);
        mLoginbtn.setOnClickListener(this);
        mTextView.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!= null){
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this, Topics.class));
            finish();
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mSignupbtn) {
            Intent signup = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(signup);
            finish();
        }

        if (view == mLoginbtn) {
            Toast.makeText(MainActivity.this, "log in", Toast.LENGTH_LONG).show();
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
            finish();
        }

        if (view == mTextView) {
            Toast.makeText(MainActivity.this, "must", Toast.LENGTH_SHORT).show();
//            switch (view)

//            Intent topics = new Intent(MainActivity.this, Topics.class);
//            startActivity(topics);
//            finish();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exit = new AlertDialog.Builder(this);
        exit.setTitle("Exit");
        exit.setMessage("Do you want to exit?");
        exit.setCancelable(false);
        exit.setPositiveButton("exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        exit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing
            }
        });
        exit.show();
    }
}