package com.sharma.deepak.popularmoviestage1.bean;

/**
 * Created by deepak on 27-05-2017.
 */

public class Reviews {
    private String author, content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Reviews(String author, String content) {

        this.author = author;
        this.content = content;
    }
}
