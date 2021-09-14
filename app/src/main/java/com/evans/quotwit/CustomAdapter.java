    package com.evans.quotwit;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.squareup.picasso.Picasso;

    import java.util.List;

    import models.Headlines;

    public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
        //objects
        private Context context;
        private List<Headlines> headlines;

        //object for contnt click listener
        private SelectListener listener;

        public CustomAdapter(Context context, List<Headlines> headlines, SelectListener listener) {
            this.context = context;
            this.headlines = headlines;
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
    }
