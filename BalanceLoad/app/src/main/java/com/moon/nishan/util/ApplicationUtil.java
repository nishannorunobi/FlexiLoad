package com.moon.nishan.util;

import android.graphics.Bitmap;
import android.os.Build;

/**
 * Created by nishan on 11/16/15.
 */
public abstract class ApplicationUtil {
    public static String getBasePackageName(){
        String name = ApplicationUtil.class.getPackage().getName();
        return name;
    }

    public static int getBitmapByteCount(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1)
            return bitmap.getRowBytes() * bitmap.getHeight();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return bitmap.getByteCount();
        return bitmap.getAllocationByteCount();
    }
}
