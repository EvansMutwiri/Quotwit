package ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evans.quotwit.ContentDetailsActivity;
import com.evans.quotwit.CustomAdapter;
import com.evans.quotwit.NewsApiResponse;
import com.evans.quotwit.OnFetchData;
import com.evans.quotwit.R;
import com.evans.quotwit.RequestManager;
import com.evans.quotwit.SelectListener;

import org.parceler.Parcels;

import java.util.List;

import models.Headlines;

public class Topics extends AppCompatActivity implements SelectListener {

    RecyclerView recyclerView;
    CustomAdapter adapter;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        loading = new ProgressDialog(this);
        loading.setTitle("Getting the latest content...");
        loading.show();


        // call get methods to get response
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general", null);
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