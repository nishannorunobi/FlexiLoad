package com.moon.nishan.ocr;


import android.app.Activity;
import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

public class TessOcr {
    private TessBaseAPI tessBaseAPI;
    private Activity context;
    public TessOcr(Activity context) {
        this.context = context;
        TessDataManager.initTessTrainedData(context);
        tessBaseAPI = new TessBaseAPI();

        //String path = "/mnt/sdcard/tesseract/tessdata/eng.traineddata";
        //TessDataManager.getTrainedDataPath();
        String path = "/data/data/com.moon.nishan.flexyload/files/tesseract";

        tessBaseAPI.setDebug(true);
        tessBaseAPI.init(path, "eng");

        //For example if we want to only detect numbers
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-qwertyuiop[]}{POIU" +
                "YTREWQasdASDfghFGHjklJKLl;L:'\"\\|~`xcvXCVbnmBNM,./<>?");
    }

    public String getOCRResult(Bitmap bitmap) {
        //bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        tessBaseAPI.setImage(bitmap);
        String result = tessBaseAPI.getUTF8Text();
        tessBaseAPI.end();
        return result;
    }

    public void onDestroy() {
        if (tessBaseAPI != null)
            tessBaseAPI.end();
    }

    /*public String detectText(Bitmap bitmap) {

        TessDataManager.initTessTrainedData(context);
        TessBaseAPI tessBaseAPI = new TessBaseAPI();

        String path = "/mnt/sdcard/packagename/tessdata/eng.traineddata";

        tessBaseAPI.setDebug(true);
        tessBaseAPI.init(path, "eng"); //Init the Tess with the trained data file, with english language

        //For example if we want to only detect numbers
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-qwertyuiop[]}{POIU" +
                "YTREWQasdASDfghFGHjklJKLl;L:'\"\\|~`xcvXCVbnmBNM,./<>?");


        tessBaseAPI.setImage(bitmap);

        String text = tessBaseAPI.getUTF8Text();

        Log.d(TAG, "Got data: " + result);
        tessBaseAPI.end();

        return text;
    }*/

}
