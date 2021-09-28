package com.evans.quotwit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class FirebaseSavedAdapter extends RecyclerView.Adapter<FirebaseSavedAdapter.SavedViewHolder> {
    ArrayList<SavedContentModel> mList;
    Context context;

    public FirebaseSavedAdapter(Context context, ArrayList<SavedContentModel> mList){
        this.mList= mList;
        this.context = context;
    }
    @NonNull
    @Override
    public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new SavedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedViewHolder holder, int position) {
        SavedContentModel model = mList.get(position);
        holder.tv_title.setText(model.getTitle());
        holder.tv_author.setText(model.getTitle());
        holder.tv_time.setText(model.getPublishedAt());
        holder.tv_detail.setText(model.getDescription());
        holder.tv_content.setText(model.getContent());
        Picasso.get().load(model.getUrlToImage()).into(holder.iv_image);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class SavedViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_author, tv_time, tv_detail, tv_content;
        ImageView iv_image;

        public SavedViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_content_detail);
            tv_author = itemView.findViewById(R.id.tv_content_detail_author);
            tv_time = itemView.findViewById(R.id.tv_content_detail_time);
            tv_detail = itemView.findViewById(R.id.tv_content_detail_detail);
            tv_content = itemView.findViewById(R.id.tv_content_detail_article);
            iv_image = itemView.findViewById(R.id.iv_content_detail);
        }
    }
}
