package com.evans.quotwit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class Topics extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

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

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        VPAdaptor vpAdaptor = new VPAdaptor(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdaptor.addFragment(new Tech(), "Tech");
        vpAdaptor.addFragment(new Life(), "Life");
        vpAdaptor.addFragment(new Ent(), "Ent");

        viewPager.setAdapter(vpAdaptor);

    }
}