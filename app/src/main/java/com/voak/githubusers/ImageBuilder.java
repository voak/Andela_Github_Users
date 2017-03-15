package com.voak.githubusers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Voak on 08-Mar-17.
 */

public class ImageBuilder extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... avatarurl) {

        Bitmap bmp = null;

        try
        {
            URL url = new URL(avatarurl[0]);
            InputStream inputStream = url.openStream();
            bmp = BitmapFactory.decodeStream(inputStream);
        }
        catch (IOException io)
        {
            Log.e("Image IO Error", io.getMessage());
        }

        return bmp;
    }
}
