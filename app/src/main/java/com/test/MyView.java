package com.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.test.tool.ImageTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-12-24.
 */
public class MyView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private MyThread myThread;
    private Context context;

    public MyView(Context context) {
        super(context);
        this.context = context;
        holder = this.getHolder();
        holder.addCallback(this);
        myThread = new MyThread(context,holder);
    }

    //在创建时激发，一般在这里调用画图的线程。
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        myThread.isRun = true;
        myThread.start();
    }
    //在surface的大小发生改变时激发
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    //销毁时激发，一般在这里将画图的线程停止、释放。
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        myThread.isRun = false;
    }

    //线程内部类
    class MyThread extends Thread
    {
        private Context context;
        private ArrayList<Rect> list;
        private SurfaceHolder holder;
        public boolean isRun ;

        public  MyThread(Context context,SurfaceHolder holder)
        {
            this.context = context;
            this.holder =holder;
            isRun = true;
            list = new PlistFile().readAnimatorPlist(context.getResources().openRawResource(R.raw.index_bird_fen_ani_667h));
        }
        @Override
        public void run()
        {
            int count = 0;
            Paint p= new Paint();
            int width = list.get(0).width();
            int height = list.get(0).height();
            while(isRun)
            {
                Canvas c = null;
                try
                {
                    synchronized (holder)
                    {
                        count= count% list.size();
                        c = holder.lockCanvas();//锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。
                        c.drawRect(0, 0,width , height, p);
                        c.drawBitmap(BaseDataManager.getInstance(context).getResDrawable(R.drawable.index_bird_fen_ani_667h,0,0),list.get(count),new Rect(0,0,c.getWidth(),c.getHeight()),p);
                        Log.i("正常111",list.get(count).toString());
                        Log.i("正常222",c.getWidth()+"/"+c.getHeight());
                        ++count;
                        Thread.sleep(500);//睡眠时间为1秒
                    }
                }
                catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally
                {
                    if(c!= null)
                    {
                        holder.unlockCanvasAndPost(c);//结束锁定画图，并提交改变。
                    }
                }
            }
        }
    }
}
