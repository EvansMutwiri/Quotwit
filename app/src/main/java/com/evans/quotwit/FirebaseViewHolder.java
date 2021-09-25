package com.evans.quotwit;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import models.Headlines;

public class FirebaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //objects for view items
        TextView text_title, text_source;
        ImageView img_headline;
        CardView cardView;
        Context mContext;

    List<Headlines> headlines;
    List<Headlines> headlinesAll;

    SelectListener listener;


        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            text_title = itemView.findViewById(R.id.text_title);
            text_source = itemView.findViewById(R.id.text_source);
            img_headline = itemView.findViewById(R.id.img_headline);
            cardView =itemView.findViewById(R.id.topics_container);
            mContext = itemView.getContext();

            this.headlinesAll = headlines;
            this.headlines = new ArrayList<>(headlinesAll);


            itemView.setOnClickListener(this);
        }

        public void bindHeadlines(Headlines headlines){
            text_title.setText(headlines.getTitle());
            text_source.setText(headlines.getSource().getName());

            if (headlines.getUrlToImage()!=null){
                Picasso.get().load(headlines.getUrlToImage()).into(img_headline);
            }
            //card onclick listener
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnContentClick(headlines);
                }
            });
        }

    @Override
    public void onClick(View view) {
            final List<Headlines> headlines = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_DETAILS).child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    headlines.add(snapshot.getValue(Headlines.class));
                }

                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, ContentDetailsActivity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("data", Parcels.wrap(headlines));
                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
