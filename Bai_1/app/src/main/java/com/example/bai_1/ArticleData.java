package com.example.bai_1;

import android.content.Context;
import android.app.Activity;
import android.widget.GridView;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.util.Log;

public class ArticleData {

    public static ArticleList data;
    private Context context;
    private GridView gridview;
    private final ExecutorService executor = Executors.newSingleThreadExecutor(); // Executor cho các tác vụ nền

    public ArticleData(Context context, GridView gridview) {
        this.context = context;
        this.gridview = gridview;
    }

    // Phương thức để lấy bài viết bằng ID
    public static Article getPhotoFromId(int id) {
        if (data != null && data.getArticles() != null) {
            for (Article article : data.getArticles()) {
                if (article.getArticle_id() == id) {
                    Log.d("ArticleData", "Found article with ID: " + id);
                    return article;
                }
            }
        }
        Log.e("ArticleData", "Article not found with ID: " + id);
        return null;
    }

    // Phương thức để tải dữ liệu từ URL
    public void loadData(String url, Activity activity) {
        executor.execute(() -> {
            // Tải file từ URL và lưu vào thư mục cache
            File file = Downloader.downloadFile(url, context.getCacheDir());

            if (file != null) {
                activity.runOnUiThread(() -> {
                    // Đọc và kiểm tra nội dung JSON
                    String jsonContent = readText(file);
                    Log.d("ArticleData", "JSON Content: " + jsonContent);

                    // Phân tích JSON thành đối tượng Java
                    Gson gson = new Gson();
                    data = gson.fromJson(jsonContent, ArticleList.class);

                    // Kiểm tra kết quả phân tích
                    if (data != null && data.getArticles() != null) {
                        Log.d("ArticleData", "Number of articles loaded: " + data.getArticles().size());

                        // Tạo adapter và hiển thị dữ liệu
                        ArticleAdapter adapter = new ArticleAdapter(data.getArticles(), context);
                        gridview.setAdapter(adapter);
                    } else {
                        Log.e("ArticleData", "Failed to parse JSON data or no articles found.");
                    }
                });
            } else {
                Log.e("ArticleData", "Failed to download file from URL: " + url);
            }
        });
    }

    // Phương thức đọc file và trả về nội dung dưới dạng chuỗi
    public String readText(File file) {
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            if (file != null && file.exists()) {
                FileInputStream stream = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }
}
