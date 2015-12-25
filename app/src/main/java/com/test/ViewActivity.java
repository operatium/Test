package com.test;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.test.tool.ImageTool;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.viewactivity)
public class ViewActivity extends Activity {
    private MyView image;

    @ViewById
    ImageView image1;
    @AfterViews
    public void init()
    {
        image = new MyView(this);
        image.layout(0,500,720,1000);
        this.addContentView(image,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Click
    public void button()
    {
//        image1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.index_bird_fen_ani_667h));
//        new FlyBird().start(Tool.getWindowWH(this).x-image.getWidth(),Tool.getWindowWH(this).y,3000,image);
    }
}
