package com.evans.quotwit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Topics extends AppCompatActivity {
    private TextView mWelcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        mWelcomeText = findViewById(R.id.welcomeTextView);
        Intent topics = getIntent();
        String username = topics.getStringExtra("username");

        mWelcomeText.setText("Hello there " + username);
    }
}