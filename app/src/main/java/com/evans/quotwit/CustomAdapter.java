    package com.evans.quotwit;

    import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import models.Headlines;

    public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> implements Filterable {
        //objects
        Context context;
        List<Headlines> headlines;
        List<Headlines> headlinesAll;

        //object for contnt click listener
        SelectListener listener;

        public CustomAdapter(Context context, List<Headlines> headlines, SelectListener listener) {
            this.context = context;
            this.headlinesAll = headlines;
            this.headlines = new ArrayList<>(headlinesAll);

            this.listener = listener;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

            //call views for title image and source
            holder.text_title.setText(headlines.get(position).getTitle());
            holder.text_source.setText(headlines.get(position).getSource().getName());

            if (headlines.get(position).getUrlToImage()!=null){
                Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.img_headline);
            }
            //card onclick listener
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnContentClick(headlines.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return headlines.size();
        }

        //headlines filter objects for search view
        @Override
        public Filter getFilter() {
            return headlinesFilter;
        }

        private Filter headlinesFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Headlines> filteredHeadlines = new ArrayList<>();
                if (charSequence == null || charSequence.length() == 0) {
                    filteredHeadlines.addAll(headlinesAll);
                } else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();

                    for (Headlines item : headlinesAll) {
                        if (item.getTitle().toLowerCase().contains(filterPattern) || item.getDescription().toLowerCase().contains(filterPattern)) {
                            filteredHeadlines.add(item);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredHeadlines;
                filterResults.count = filteredHeadlines.size();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                headlines.clear();
                headlines.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
