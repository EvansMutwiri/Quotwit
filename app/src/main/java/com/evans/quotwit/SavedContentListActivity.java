package com.evans.quotwit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import models.Headlines;
import ui.Topics;

public class SavedContentListActivity extends AppCompatActivity {

    private DatabaseReference mUserRef;
    private FirebaseRecyclerAdapter<Headlines, FirebaseViewHolder> mFirebaseAdapter;

    @BindView(R.id.recycler_main)
    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mUserRef = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_DETAILS)
                .child(uid);
        setUpFirebaseAdapter();
        loading = new ProgressDialog(this);
        loading.setTitle("Getting the latest content...");
        loading.show();

        //bottom nav
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);

        //item selector listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.saved:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Topics.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                        return true;
                }
                return false;
            }
        });
        loading.dismiss();
        showHeadlines();
    }

    private void setUpFirebaseAdapter() {
        FirebaseRecyclerOptions<Headlines> options =
                new FirebaseRecyclerOptions.Builder<Headlines>()
                        .setQuery(mUserRef, Headlines.class)
                        .build();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Headlines, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, int position, @NonNull Headlines headlines) {
                firebaseViewHolder.bindHeadlines(headlines);
            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_topics, parent, false);
                return new FirebaseViewHolder(view);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mFirebaseAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }

    private void showHeadlines() {
        recyclerView.setVisibility(View.VISIBLE);
    }

}