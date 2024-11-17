package com.example.bai_1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ArticleList {

    @SerializedName("articles")
    @Expose
    private ArrayList<Article> articles;

    // Constructor to initialize the list of articles
    public ArticleList(ArrayList<Article> articles) {
        this.setArticles(articles);
    }

    // Getter method for articles
    public ArrayList<Article> getArticles() {
        return articles;
    }

    // Setter method for articles
    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
}
