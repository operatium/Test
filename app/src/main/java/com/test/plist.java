package com.test;

import android.graphics.Point;

/**
 * Created by Administrator on 2015-12-24.
 */
public class plist {
    Point point;
    int width;
    int height;

    public plist(String str1,String str2,String str3,String str4) {
        this.point = new Point(Integer.valueOf(str1),Integer.valueOf(str2));
        this.width = Integer.valueOf(str3);
        this.height = Integer.valueOf(str4);
    }

    @Override
    public String toString() {
        return "plist{" +
                "point=" + point +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
