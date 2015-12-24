package com.test;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 ** Created by yuexing on 2015/10/28.
 */
public class BitmapCache implements ImageLoader.ImageCache {
    private LruCache<String,Bitmap> lruCache;

    public BitmapCache() {
        int maxMemory = 10*1024*1024;
        lruCache = new LruCache<String, Bitmap>(maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String httpUrl) {
        Bitmap bitmap = lruCache.get(httpUrl);
        if (bitmap==null)
        {
//            Log.i("正常",httpUrl+"内存中mei找到");
        }
        else {
//            Log.i("正常", httpUrl + "内存中找到");
        }
        return bitmap;
    }

    @Override
    public void putBitmap(String httpUrl, Bitmap bitmap) {
        if (httpUrl!=null) {
//            Log.i("正常",httpUrl+"写入缓存w="+bitmap.getRowBytes()+"/h="+bitmap.getHeight());
            lruCache.put(httpUrl, bitmap);
        }
    }

    public Bitmap getResource(int res)
    {
        Bitmap bitmap = lruCache.get("R.drawable."+res);
        if (bitmap==null)
        {
//            Log.i("正常",res+"内存中mei找到");
        }
        else {
//            Log.i("正常", res + "内存中找到");
        }
        return bitmap;
    }

    public void putResource(int res,Bitmap bitmap)
    {
//        Log.i("正常",res+"写入缓存");
        lruCache.put("R.drawable." + res, bitmap);
    }

    public Bitmap remove(String httpUrl)
    {
        return  lruCache.remove(httpUrl);
    }

    public void putUri(Uri uri,Bitmap bitmap,int width ,int height)
    {
        if (bitmap!=null) {
//            Log.i("正常","W"+width+"H"+height+uri.toString()+"写入缓存");
//            Log.i("正常","位图占内存"+bitmap.getByteCount()/1024/1024+"M="+bitmap.getRowBytes()+"*"+bitmap.getHeight());
            lruCache.put("W"+width+"H"+height+uri.toString(), bitmap);
        }
    }

    public Bitmap getUri(Uri uri,int width ,int height)
    {
        Bitmap bitmap = lruCache.get("W"+width+"H"+height+uri.toString());
        if (bitmap==null)
        {
//            Log.i("正常","W"+width+"H"+height+uri.toString()+"内存中mei找到");
        }
        else {
//            Log.i("正常","W"+width+"H"+height+uri.toString() + "内存中找到");
        }
        return bitmap;
    }
}
