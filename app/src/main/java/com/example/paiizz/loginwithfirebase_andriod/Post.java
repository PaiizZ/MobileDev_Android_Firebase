package com.example.paiizz.loginwithfirebase_andriod;

public class Post {
    private String imageURL, imageTitle;

    public Post() {}

    public Post(String imageURL, String imageTitle) {
        this.imageURL = imageURL;
        this.imageTitle = imageTitle;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    @Override
    public String toString() {
        return "URL: " + this.imageURL + ", TITLE: " + this.imageTitle;
    }
}