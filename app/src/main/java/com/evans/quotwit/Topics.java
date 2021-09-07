package com.evans.quotwit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Topics extends AppCompatActivity {

    //topics list
    private final String[] topics = new String[] {
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

//        ListView mListView = findViewById(R.id.topics_listView);
//        TextView mWelcomeText = findViewById(R.id.welcomeTextView);

//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, topics);
//        mListView.setAdapter(adapter);
//
//        Intent topics = getIntent();
//        String username = topics.getStringExtra("username");
//
//        mWelcomeText.setText(String.format("Hello there %s", username));

    }
}