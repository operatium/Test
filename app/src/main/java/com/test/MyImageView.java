package com.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-12-24.
 */
public class MyImageView extends View {
    private Bitmap bitmap;
    private ArrayList<Rect> plists;
    private int index=0;

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs, Bitmap bitmap) {
        super(context, attrs);
        this.bitmap = bitmap;
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr, Bitmap bitmap) {
        super(context, attrs, defStyleAttr);
        this.bitmap = bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        index = index % plists.size();
        canvas.drawBitmap(bitmap,plists.get(index),new Rect(0,0,100,100),new Paint());
        ++index;
    }

    public void setPlists(ArrayList<Rect> plists) {
        this.plists = plists;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
