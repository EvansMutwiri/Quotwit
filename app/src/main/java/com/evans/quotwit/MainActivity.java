package com.evans.quotwit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mSignupbtn.setOnClickListener(this);
        mLoginbtn.setOnClickListener(this);
        mTextView.setOnClickListener(this);
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
            Toast.makeText(MainActivity.this, "skip", Toast.LENGTH_SHORT).show();
            Intent topics = new Intent(MainActivity.this, Topics.class);
            startActivity(topics);
            finish();
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