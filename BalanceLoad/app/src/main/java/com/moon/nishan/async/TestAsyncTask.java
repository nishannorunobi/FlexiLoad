package com.moon.nishan.async;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by nishan on 12/1/15.
 */
public class TestAsyncTask extends AsyncTask<Integer, Void, Bitmap> {

    public TestAsyncTask(ImageView imageView) {
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {
        return null;
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {

    }
}