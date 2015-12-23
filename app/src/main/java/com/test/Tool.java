package com.test;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

/**
 * Created by Administrator on 2015-12-23.
 */
public class Tool {

    void birdFly()
    {

    }

    //三阶贝塞尔曲线方程
    static double bezierat( double a, double b, double c, double d, float t )
    {
        return (Math.pow(1-t,3) * a +
                3*t*(Math.pow(1-t,2))*b +
                3*Math.pow(t,2)*(1-t)*c +
                Math.pow(t,3)*d );
    }

    static Point getWindowWH(Activity activity)
    {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }
}
