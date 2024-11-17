package com.example.bai_1;

import android.os.Bundle;
import android.util.Log;
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

        // Khởi tạo các thành phần giao diện
        iv_detail = findViewById(R.id.iv_detail);
        tv_detail_title = findViewById(R.id.tv_detail_title);
        tv_detail_description = findViewById(R.id.tv_detail_description);

        // Nhận ID của bài viết từ Intent
        int id = getIntent().getIntExtra("id", -1); // Đặt mặc định -1 nếu ID không tồn tại

        Log.d("ViewArticleActivity", "Received article ID: " + id); // Kiểm tra ID

        if (id != -1) { // Chỉ tiếp tục nếu ID hợp lệ
            Article article = ArticleData.getPhotoFromId(id);
            if (article != null) {
                Log.d("ViewArticleActivity", "Article found: " + article.getArticle_title());

                // Sử dụng Picasso để tải ảnh
                Picasso.get()
                        .load(article.getArticle_image())
                        .resize(400, 500)
                        .centerCrop()
                        .into(iv_detail);

                // Hiển thị tiêu đề và mô tả
                tv_detail_title.setText(article.getArticle_title());
                tv_detail_description.setText(article.getArticle_description());
            } else {
                Log.e("ViewArticleActivity", "Article not found with ID: " + id);
                showErrorMessage();
            }
        } else {
            Log.e("ViewArticleActivity", "Invalid article ID received: " + id);
            showErrorMessage();
        }
    }

    // Phương thức để hiển thị thông báo lỗi
    private void showErrorMessage() {
        tv_detail_title.setText("Không tìm thấy bài viết");
        tv_detail_description.setText("Không thể hiển thị chi tiết.");
    }
}
