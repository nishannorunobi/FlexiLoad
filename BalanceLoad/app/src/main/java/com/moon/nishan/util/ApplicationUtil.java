package com.moon.nishan.util;

/**
 * Created by nishan on 11/16/15.
 */
public abstract class ApplicationUtil {
    public static String getBasePackageName(){
        String name = ApplicationUtil.class.getPackage().getName();
        return name;
    }
}
