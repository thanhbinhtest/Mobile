package com.example.bai_1;

import android.content.Context;
import android.app.Activity; // Import Activity class
import android.widget.GridView;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArticleData {

    public static ArticleList data;
    private Context context;
    private GridView gridview;
    private final ExecutorService executor = Executors.newSingleThreadExecutor(); // Executor for background tasks

    public ArticleData(Context context, GridView gridview) {
        this.context = context;
        this.gridview = gridview;
    }

    // Method to get an article by ID
    public static Article getPhotoFromId(int id) {
        for (int i = 0; i < data.getArticles().size(); i++) {
            if (data.getArticles().get(i).getArticle_id() == id) {
                return data.getArticles().get(i); // Return the article with matching ID
            }
        }
        return null; // Return null if no article with the given ID is found
    }

    // Method to load data from URL
    public void loadData(String url, Activity activity) {
        // Execute background task using ExecutorService
        executor.execute(() -> {
            // Download the file from the given URL and store it in cache directory
            File file = Downloader.downloadFile(url, context.getCacheDir());

            if (file != null) {
                // Run the UI update code on the main thread after downloading the file
                activity.runOnUiThread(() -> {
                    // Parse the downloaded file using Gson
                    Gson gson = new Gson();
                    data = gson.fromJson(readText(file), ArticleList.class); // Assuming readText() reads the file content into a string

                    // Create an adapter with the articles from the parsed data
                    ArticleAdapter adapter = new ArticleAdapter(data.getArticles(), context);
                    // Set the adapter to the GridView to display the articles
                    gridview.setAdapter(adapter);
                });
            }
        });
    }

    // Method to read the file and return its content as a string
    public String readText(File file) {
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            if (file != null && file.exists()) {  // Check if the file is not null and exists
                FileInputStream stream = new FileInputStream(file);  // Open the file input stream
                reader = new BufferedReader(new InputStreamReader(stream)); // Wrap in BufferedReader for efficiency
                String line;
                while ((line = reader.readLine()) != null) {  // Read each line of the file
                    stringBuilder.append(line).append("\n");  // Append each line to the StringBuilder
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Print the stack trace if there's an error
        } finally {
            try {
                if (reader != null) {
                    reader.close();  // Always close the reader in the finally block
                }
            } catch (IOException e) {
                e.printStackTrace();  // Handle any IOExceptions that occur when closing the reader
            }
        }

        return stringBuilder.toString();  // Return the full content of the file as a string
    }
}
