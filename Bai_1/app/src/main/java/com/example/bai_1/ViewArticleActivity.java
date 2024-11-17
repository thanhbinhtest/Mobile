package com.example.bai_1;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ViewArticleActivity extends AppCompatActivity {

    ImageView iv_detail;
    TextView tv_detail_title, tv_detail_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article);

        // Initialize views
        iv_detail = findViewById(R.id.iv_detail);
        tv_detail_title = findViewById(R.id.tv_detail_title);
        tv_detail_description = findViewById(R.id.tv_detail_description);

        // Retrieve the article ID from the intent
        int id = getIntent().getIntExtra("id", 0);

        // Use Picasso to load the image
        Picasso.get()
                .load(ArticleData.getPhotoFromId(id).getArticle_image())
                .resize(400, 500)
                .centerCrop()
                .into(iv_detail);

        // Set the title and description
        tv_detail_title.setText(ArticleData.getPhotoFromId(id).getArticle_title());
        tv_detail_description.setText(ArticleData.getPhotoFromId(id).getArticle_description());
    }
}
