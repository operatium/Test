package com.test;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Administrator on 2015-12-23.
 */
@EActivity(R.layout.viewactivity)
public class ViewActivity extends Activity {
    @ViewById
    ImageView image;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        new FlyBird().move(Tool.getWindowWH(this).x-image.getWidth(),Tool.getWindowWH(this).y,3000,image);
        return true;
    }

    /**
     * 按直线轨迹运动
     */
    public void moveLinePath() {
        final int width = Tool.getWindowWH(this).x-100;
        final int height = Tool.getWindowWH(this).y-100;
        ValueAnimator xValue = ValueAnimator.ofFloat(0, 1);
        xValue.setDuration(3000);
        xValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int x = (int) Tool.bezierat(0,width/3,width*2/3,width, (Float) animation.getAnimatedValue());
                int y = (int) Tool.bezierat(0,height,height,0, (Float) animation.getAnimatedValue());
                Log.i("正常201512231902",(animation.getAnimatedValue())+"");
                System.out.println("debug:(x,y) = " + x + "," + y);
                moveViewByLayout(image, x, y);
            }
        });
        xValue.setInterpolator(new LinearInterpolator());
        xValue.start();
    }

    void moveViewByLayout(View view, int rawX, int rawY) {
        if (rawX>Tool.getWindowWH(this).x)
            rawX = Tool.getWindowWH(this).x;
        if (rawY>Tool.getWindowWH(this).y)
            rawY = Tool.getWindowWH(this).y;
        int left = rawX;
        int top = rawY;
        int width = left + view.getWidth();
        int height = top + view.getHeight();
        view.layout(left, top, width, height);
    }
}
