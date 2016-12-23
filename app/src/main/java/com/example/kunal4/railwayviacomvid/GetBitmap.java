package com.example.kunal4.railwayviacomvid;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.net.URL;
import java.util.HashMap;

/**
 * Created by kunal4 on 12/23/16.
 */

public class GetBitmap extends AsyncTask<Void,Void,Void> {

    private Context context;
    private String[] links;
    private ProgressDialog loading;
    private ListVideoActivity mainActivity;

    public GetBitmap(Context context, ListVideoActivity mainActivity, String[] links){
        this.context = context;
        this.links = links;
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(context,"Getting your videos","It won't take much long. Promise.",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        loading.dismiss();
        mainActivity.showData();
    }

    @Override
    protected Void doInBackground(Void... params) {
        for(int i=0; i<links.length; i++){
            try {
                Log.d("LINK",links[i]);
                Config.bitmaps[i] = retriveVideoFrameFromVideo(links[i]);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return null;
    }

//    private Bitmap getImage(String bitmapUrl){
//        URL url;
//        Bitmap image = null;
//        try {
//            url = new URL(bitmapUrl);
//            image = retriveVideoFrameFromVideo(bitmapUrl);
//        }catch(Exception e){} catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        return image;
//    }


    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        }
        finally
        {
            if (mediaMetadataRetriever != null)
            {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
}