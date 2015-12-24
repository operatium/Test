package com.test;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.test.tool.ImageTool;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 ** Created by yuexing on 2015/10/26.
 * 建立数据管理类
 *  处理数据缓存 先在内存建立数据缓冲  再在文件夹内建立一个文件缓存  最后向网络获取
 *1.内存数据队列
 * 2.文件夹数据列表----路径存入数据库 文件放在指定文件夹内
 * 3.网络等待 和下载队列
 */
public class BaseDataManager {
    private BitmapCache m_MemoryCache;
    private HashMap<String,Handler> m_NetWaitList;
    private HashMap<String,Handler> m_DownLoadList;
    private Context context;
    private String ImagePathHead;
    private RequestQueue m_queue;
    private ImageLoader m_imageLoader;
    private ImageLoader.ImageListener listener;
    private static BaseDataManager baseDataManager;


    public BaseDataManager(Context context) {
        this.context =context;
        m_queue = new Volley().newRequestQueue(context);
        m_imageLoader = new ImageLoader(m_queue,new BitmapCache());
        m_MemoryCache = new BitmapCache();
        //TODO 数据库初始化
//        dataInit = new DataInit(context,true,"cxlin");
    }

    public static BaseDataManager getInstance(Context context)
    {
        if (baseDataManager==null)
            baseDataManager=new BaseDataManager(context);
        return baseDataManager;
    }

    public ImageLoader getM_imageLoader() {
        return m_imageLoader;
    }

    public BitmapCache getCache() {
        return m_MemoryCache;
    }

    public Bitmap getResDrawable(int res,int width ,int height)
    {
        Bitmap bitmap=null;
        try
        {
            bitmap = m_MemoryCache.getResource(res);
            if (bitmap==null)
            {
                //内存找不到
                if (width == 0 || height == 0)
                {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeResource(context.getResources(),res, options);
                    m_MemoryCache.putResource(res,bitmap);
                }
                else
                {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeResource(context.getResources(), res, options);
                    options.inSampleSize = ImageTool.calculateInSampleSize(options, width, height,false);
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeResource(context.getResources(),res, options);
                    m_MemoryCache.putResource(res, bitmap);
                }
            }
            else
            {
//                Log.i("正常","R.drawable."+res+"内存中找到"+"w="+width+ "/h="+height);
                return bitmap;
            }
        }
        catch (Exception e)
        {
            Log.e("异常201510271840",e.toString());
        }
        return bitmap;
    }

    public Bitmap getUri(Uri uri,int width ,int height,boolean IsMax) throws Exception {
        Bitmap bitmap= m_MemoryCache.getUri(uri,width,height);
        if (bitmap == null)
        {
            bitmap = ImageTool.readUri(uri,context,width,height,IsMax);
            if (bitmap == null)
            {
                throw new Exception("位图空指针");
            }
            else
            {
                m_MemoryCache.putUri(uri,bitmap,width,height);
                return bitmap;
            }
        }
        else
        {
            return bitmap;
        }
    }


    /**
     * 判断包名所对应的应用是否安装在SD卡上
     * @return, true if install on SD card
     */
    public static boolean isInstallOnSDCard(Context context) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        ApplicationInfo appInfo;
        try {
            appInfo = pm.getApplicationInfo(context.getPackageName(), 0);
            if ((appInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("异常201511171028",e.toString());
        }
        return false;
    }

}
