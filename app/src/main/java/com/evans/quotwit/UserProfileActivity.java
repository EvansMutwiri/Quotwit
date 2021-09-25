package com.evans.quotwit;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ui.Topics;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseUser fUser;
    private DatabaseReference databaseReference;
    private String userID;


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = fUser.getUid();

        final TextView welcomeText = (TextView) findViewById(R.id.welcome);
        final TextView profileName = (TextView) findViewById(R.id.user_prof_name);
        final TextView userEmail = (TextView) findViewById(R.id.user_email);
        final ImageView profileImage = (ImageView) findViewById(R.id.profile_image);

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                String name = userProfile.name;
                String email = userProfile.email;

                welcomeText.setText("Welcome");
                profileName.setText("Signed in as "+ name);
                userEmail.setText("Email " + email);
//                profileImage.setImageDrawable();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //bottom nav
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        //item selector listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.saved:
                        startActivity(new Intent(getApplicationContext(), SavedContentActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Topics.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });
    }
}