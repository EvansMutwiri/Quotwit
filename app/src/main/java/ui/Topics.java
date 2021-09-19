package ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evans.quotwit.ContentDetailsActivity;
import com.evans.quotwit.CustomAdapter;
import com.evans.quotwit.MainActivity;
import com.evans.quotwit.NewsApiResponse;
import com.evans.quotwit.OnFetchData;
import com.evans.quotwit.R;
import com.evans.quotwit.RequestManager;
import com.evans.quotwit.SelectListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.parceler.Parcels;

import java.util.List;

import models.Headlines;

public class Topics extends AppCompatActivity implements SelectListener {

    RecyclerView recyclerView;
    CustomAdapter adapter;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {
                    getSupportActionBar().setTitle("Welcome, to Qtwit");
                }
            }
        };

        loading = new ProgressDialog(this);
        loading.setTitle("Getting the latest content...");
        loading.show();


        // call get methods to get response
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general", null);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    //Creating and Inflating an Overflow Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(Topics.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private final OnFetchData<NewsApiResponse> listener = new OnFetchData<NewsApiResponse>() {
        @Override
        public void onFetchData(List<Headlines> list, String message) {
            showNews(list);
            loading.dismiss();
        }

        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List<Headlines> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnContentClick(Headlines headlines) {
        String headline = "Qtwit";
        Toast.makeText(this, headline, Toast.LENGTH_LONG).show();
        startActivity(new Intent(Topics.this, ContentDetailsActivity.class)
        .putExtra("data", Parcels.wrap(headlines)));
    }
}