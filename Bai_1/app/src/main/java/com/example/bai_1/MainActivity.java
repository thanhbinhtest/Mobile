package com.example.bai_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.AdapterView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public GridView gridview;  // GridView to display articles

    private AdapterView.OnItemClickListener onitemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Start a new activity to view article details
            Intent intent = new Intent(getBaseContext(), ViewArticleActivity.class);
            intent.putExtra("id", gridview.getAdapter().getItemId(position)); // Passing article ID
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gridview = findViewById(R.id.gridview);

        // Load data into GridView
        new ArticleData(getBaseContext(), gridview).loadData(
                "https://raw.githubusercontent.com/thanhdnh/json/main/products.json",
                this);

        // Set onItemClickListener to handle item clicks in GridView
        gridview.setOnItemClickListener(onitemClick);
    }
}
