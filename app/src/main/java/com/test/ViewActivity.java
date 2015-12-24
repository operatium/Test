package com.test;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.viewactivity)
public class ViewActivity extends Activity {
    private MyView image;

    @ViewById
    LinearLayout line;
    @AfterViews
    public void init()
    {
        image = new MyView(this);
        line.addView(image);
    }

    @Click
    public void button()
    {
        new FlyBird().start(Tool.getWindowWH(this).x-line.getWidth(),Tool.getWindowWH(this).y,3000,line);
    }
}
