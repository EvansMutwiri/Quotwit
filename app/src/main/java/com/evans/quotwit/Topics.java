package com.evans.quotwit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class Topics extends AppCompatActivity {
    private TextView mWelcomeText;
    private ListView mListView;

    //topics list
    private String[] topics = new String[] {
            "Radio", "Movies",
            "Soccer", "Consumer products", "Poetry", "Books and Literature",
            "Music", "Lyrics", "Programming", "Geek jokes",
            "News", "Astronomy", "Politics",
            "Cars", "Technology"
    };

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