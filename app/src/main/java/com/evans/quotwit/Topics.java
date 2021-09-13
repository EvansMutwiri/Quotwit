package com.evans.quotwit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import models.Headlines;
import models.NewsApiResponse;

public class Topics extends AppCompatActivity {

    //topics list
//    private final String[] topics = new String[] {
//            "Radio", "Movies",
//            "Soccer", "Consumer products", "Poetry", "Books and Literature",
//            "Music", "Lyrics", "Programming", "Geek jokes",
//            "News", "Astronomy", "Politics",
//            "Cars", "Technology"
//    };
    RecyclerView recyclerView;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

//        ListView mListView = findViewById(R.id.topics_listView);
//        TextView mWelcomeText = findViewById(R.id.welcomeTextView);

//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, topics);
//        mListView.setAdapter(adapter);

//        Intent topics = getIntent();
//        String username = topics.getStringExtra("username");
//
//        mWelcomeText.setText(String.format("Hello there %s", username));

        // call get methods to get response
        Requests manager = new Requests(this);
        manager.getNewsHeadlines(listener, "general", null);
    }

    private final OnFetchData<NewsApiResponse> listener = new OnFetchData<NewsApiResponse>() {
        @Override
        public void onFetchData(List<Headlines> list, String message) {
            showNews(list);
        }

        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List<Headlines> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
}