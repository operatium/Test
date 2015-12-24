package com.test;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2015-12-23.
 */
public class FlyBird {

    public void start(final int width , final int height, int time, final View view) {
        ValueAnimator xValue = ValueAnimator.ofFloat(0, 1);
        xValue.setDuration(time);
        xValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int x = (int) bezierat(0,width/3,width*2/3,width, (Float) animation.getAnimatedValue());
                int y = (int) bezierat(0,height,height,0, (Float) animation.getAnimatedValue());
                moveViewByLayout(view, x, y);
                fly(view);
            }
        });
        xValue.setInterpolator(new LinearInterpolator());
        xValue.start();
    }

    public void fly(final View view)
    {

    }

    private void moveViewByLayout(View view, int rawX, int rawY) {
        int left = rawX;
        int top = rawY;
        int width = left + view.getWidth();
        int height = top + view.getHeight();
        view.layout(left, top, width, height);
    }
    //三阶贝塞尔曲线方程
    static double bezierat( double a, double b, double c, double d, float t )
    {
        return (Math.pow(1-t,3) * a +
                3*t*(Math.pow(1-t,2))*b +
                3*Math.pow(t,2)*(1-t)*c +
                Math.pow(t,3)*d );
    }

}
