package com.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

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
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
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
//                        Bitmap bitmap = BaseDataManager.getInstance(context).getResDrawable(R.drawable.index_bird_fen_ani_667h,0,0);
                        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.index_bird_fen_ani_667h);
                        c.drawBitmap(bitmap,0,0,null);
                        Log.i("正常111",list.get(count).toString());
                        Log.i("正常222",bitmap.getWidth()/4+"/"+bitmap.getHeight());
                        ++count;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally
                {
                    if(c!= null)
                    {
                        holder.unlockCanvasAndPost(c);//结束锁定画图，并提交改变。
                        try {
                            sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}