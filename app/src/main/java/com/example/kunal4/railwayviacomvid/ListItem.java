package com.example.kunal4.railwayviacomvid;

import android.graphics.Bitmap;

/**
 * Created by kunal4 on 12/23/16.
 */

public class ListItem {

    private String name;
    private String rating;
    private Bitmap image;
    private String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String  getLink(){
        return link;
    }

}