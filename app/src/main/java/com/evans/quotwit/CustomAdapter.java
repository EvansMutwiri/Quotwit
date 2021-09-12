package com.evans.quotwit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import models.Headlines;

public class CustomAdapter extends RecyclerView.Adapter<TopicsViewHolder> {
    //objects
    private Context context;
    private List<Headlines> headlines;

    public CustomAdapter(Context context, List<Headlines> headlines) {
        this.context = context;
        this.headlines = headlines;
    }

    @NonNull
    @Override
    public TopicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopicsViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull TopicsViewHolder holder, int position) {

        //call views for title image and source
        holder.text_title.setText(headlines.get(position).getTitle());
        holder.text_source.setText(headlines.get(position).getSource().getName());

        if (headlines.get(position).getUrlToImage()!=null){
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.img_headline);
        }
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }
}
