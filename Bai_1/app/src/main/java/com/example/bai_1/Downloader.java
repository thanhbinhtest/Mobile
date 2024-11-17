package com.example.bai_1;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Downloader {

    // Cached file path (not used in your method, can be part of class-level variables)
    public static String cached_file_path = "";

    // Method to download the file
    public static File downloadFile(String url, File cached) {
        OkHttpClient client = new OkHttpClient(); // HTTP client
        Request request = new Request.Builder().url(url).build(); // Build request

        try ( Response response = client.newCall(request).execute();) {
            // Execute the request

            if (!response.isSuccessful()) {
                return null; // Return null if request failed
            }

            String contentType = response.header("Content-Type", "");
            String extension = getExtensionFromMimeType(contentType);

            // Create temp file with appropriate extension
            File file = File.createTempFile("downloaded_file", extension, cached);

            // If body exists, write it to the file
            if (response.body() != null) {
                BufferedSink sink = Okio.buffer(Okio.sink(file));
                sink.writeAll(response.body().source());
                sink.close();
                return file;
            }

        } catch (IOException e) {
            e.printStackTrace(); // Log the error
          // Return null in case of an error
        }
        return null;
    }

    // Method to get the file extension from mime type
    private static String getExtensionFromMimeType(String mimeType) {
        Map<String, String> mimeMap = new HashMap<>();
        mimeMap.put("image/jpeg", ".jpg");
        mimeMap.put("image/png", ".png");
        mimeMap.put("application/json", ".json");

        // Return the extension or empty string if mimeType not found
        return mimeMap.getOrDefault(mimeType, "");
    }
}
