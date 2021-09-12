package com.evans.quotwit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TopicsViewHolder extends RecyclerView.ViewHolder {

    //objects for view items
    TextView text_title, text_source;
    ImageView img_headline;
    CardView cardView;

    public TopicsViewHolder(@NonNull View itemView) {
        super(itemView);

        text_title = itemView.findViewById(R.id.tv_title);
        text_source = itemView.findViewById(R.id.tv_source);
        img_headline = itemView.findViewById(R.id.img_post);
        cardView =itemView.findViewById(R.id.topics_container);
    }
}
