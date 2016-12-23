package com.example.kunal4.railwayviacomvid;

import android.graphics.Bitmap;

/**
 * Created by kunal4 on 12/21/16.
 */


public class Config {

    public static String[] names;
    public static String[] links;
    public static String[] ratings;
    public static Bitmap[] bitmaps;


    public static final String TAG_NAME = "Name";
    public static final String TAG_RATING = "Rating";
    public static final String TAG_LINK = "Link";




    public Config(int i){
        names = new String[i];
        links = new String[i];
        ratings = new String[i];
        bitmaps = new Bitmap[i];
    }
}