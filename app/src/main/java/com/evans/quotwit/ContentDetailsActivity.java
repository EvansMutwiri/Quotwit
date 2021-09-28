    package com.evans.quotwit;

    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import models.Headlines;
    import ui.LoginActivity;

    public class ContentDetailsActivity extends AppCompatActivity implements View.OnClickListener{

        Headlines headlines;
        TextView tv_title, tv_author, tv_time, tv_detail, tv_content;
        ImageView iv_image;
        Button sharebtn;
        Button save_button;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_content_details);

            tv_title = findViewById(R.id.tv_content_detail);
            tv_author = findViewById(R.id.tv_content_detail_author);
            tv_time = findViewById(R.id.tv_content_detail_time);
            tv_detail = findViewById(R.id.tv_content_detail_detail);
            tv_content = findViewById(R.id.tv_content_detail_article);
            iv_image = findViewById(R.id.iv_content_detail);
            sharebtn = findViewById(R.id.share_button);
            save_button = findViewById(R.id.save_button);


            //unwrap parcelable
            headlines = Parcels.unwrap(getIntent().getParcelableExtra("data"));

            tv_title.setText(headlines.getTitle());
            tv_author.setText(headlines.getAuthor());
            tv_time.setText(headlines.getPublishedAt());
            tv_detail.setText(headlines.getDescription());
            tv_content.setText(headlines.getContent());
            Picasso.get().load(headlines.getUrlToImage()).into(iv_image);
            save_button.setOnClickListener(this);

            sharebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    String details = "Check out this trending topic";
                    String link = headlines.getUrl();

                    share.putExtra(Intent.EXTRA_TEXT, details);
                    share.putExtra(Intent.EXTRA_TEXT, link);
                    startActivity(Intent.createChooser(share, "Share with:"));
                }
            });

        }

        @Override
        public void onClick(View view) {
            if (view == save_button) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user !=null){
                    String uid = user.getUid();
                    DatabaseReference detailsRef = FirebaseDatabase
                            .getInstance()
                            .getReference(Constants.FIREBASE_CHILD_DETAILS)
                            .child(uid);

                    DatabaseReference pushRef = detailsRef.push();
                    String pushId = pushRef.getKey();
                    headlines.setPushId(pushId);
                    pushRef.setValue(headlines);
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Not allowed");
                    alert.setMessage("You must be signed in to save!");
                    alert.setPositiveButton("Signin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ContentDetailsActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    alert.create().show();
                }
            }
        }
    }