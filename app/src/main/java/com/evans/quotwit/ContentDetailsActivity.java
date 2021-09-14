    package com.evans.quotwit;

    import androidx.appcompat.app.AppCompatActivity;

    import android.os.Bundle;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.squareup.picasso.Picasso;

    import org.parceler.Parcels;

    import models.Headlines;

    public class ContentDetailsActivity extends AppCompatActivity {

        Headlines headlines;
        TextView tv_title, tv_author, tv_time, tv_detail, tv_content;
        ImageView iv_image;

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

            //unwrap parcelable
            headlines = Parcels.unwrap(getIntent().getParcelableExtra("data"));

            tv_title.setText(headlines.getTitle());
            tv_author.setText(headlines.getAuthor());
            tv_time.setText(headlines.getPublishedAt());
            tv_detail.setText(headlines.getDescription());
            tv_content.setText(headlines.getContent());
            Picasso.get().load(headlines.getUrlToImage()).into(iv_image);

        }
    }