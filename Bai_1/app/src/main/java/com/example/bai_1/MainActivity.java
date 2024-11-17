package com.example.bai_1;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.AdapterView;
import android.view.View;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public GridView gridview;  // GridView để hiển thị các bài viết

    private AdapterView.OnItemClickListener onitemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Lấy ID của bài viết từ GridView
            long articleId = gridview.getAdapter().getItemId(position);
            Log.d("MainActivity", "Article ID being passed: " + articleId); // Kiểm tra ID

            // Kiểm tra nếu ID hợp lệ trước khi truyền vào Intent
            if (articleId != -1) {
                Intent intent = new Intent(getBaseContext(), ViewArticleActivity.class);
                intent.putExtra("id", (int) articleId); // Ép kiểu sang int cho phù hợp với ViewArticleActivity
                startActivity(intent);
            } else {
                Log.e("MainActivity", "Invalid article ID: " + articleId);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridview = findViewById(R.id.gridview);

        // Load dữ liệu vào GridView
        new ArticleData(getBaseContext(), gridview).loadData(
                "https://raw.githubusercontent.com/thanhdnh/json/main/products.json",
                this);

        // Set onItemClickListener để xử lý khi nhấn vào mục trong GridView
        gridview.setOnItemClickListener(onitemClick);
    }
}
