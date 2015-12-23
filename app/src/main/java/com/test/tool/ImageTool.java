package com.test.tool;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 ** Created by yuexing on 2015/8/25.
 */
public class ImageTool {

    //根据图片字段拼成URL
    //img_cuser 头像
    //img_question 问题图片
    //img_chatLog 聊天图片

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;// 获取资源图片
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight,false);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight,boolean IsMax) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.i("正常","前W"+width+"H"+height);
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (IsMax)
            {
                int inSampleSize_h = (int) Math.ceil((float) height / (float) reqHeight);
                int inSampleSize_w = (int) Math.ceil((float)width / (float)reqWidth);
                inSampleSize = inSampleSize_h>inSampleSize_w?inSampleSize_h:inSampleSize_w;
                int zhishu = 0;
                while (inSampleSize!=(int) Math.pow(2,zhishu))
                {
                    if (inSampleSize>(int) Math.pow(2,zhishu))
                    {
                        ++zhishu;
                    }
                    else
                    {
                        ++inSampleSize;
                    }
                }
            }
            else
            {
                if (width > height)
                {
                    inSampleSize = (int) Math.ceil((float) height / (float) reqHeight);
                }
                else
                {
                    inSampleSize = (int) Math.ceil((float)width / (float)reqWidth);
                }
            }
        }
        Log.i("正常","后W"+reqWidth+"H"+reqHeight);
        return inSampleSize;
    }

    public static Bitmap readBitmap(Context context, int resId,int width,int height)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.outWidth=width;
        opt.outHeight=height;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }


    public static Bitmap readBitmap(Context context, int resId)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public static void Delete(Bitmap bmp)
    {
        if (!bmp.isRecycled())
        {
            bmp.recycle();
            System.gc();
        }
    }

    /**
     * 图片变为圆角
     * @param bitmap:传入的bitmap
     * @param pixels：圆角的度数，值越大，圆角越大
     * @return bitmap:加入圆角的bitmap
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels)
    {
        if(bitmap == null)
            return null;
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    public static Point getScreenSize(Context context)
    {
        Display display =((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static Bitmap readUri(Uri uri,Context context, int reqWidth, int reqHeight,boolean IsMax) {
        try {
            String path = getPath(context,uri);
            Log.i("正常",uri.toString()+"路径="+path);
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;// 获取资源图片
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight,IsMax);
            options.inJustDecodeBounds = false;
            Log.i("正常", "比例" + options.inSampleSize);
            return BitmapFactory.decodeFile(path, options);
        }
        catch (Exception e)
        {
            Log.e("异常201511020932",e.toString());
            return null;
        }
    }

    /**
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static void saveFile(Bitmap bm, String fileName,String path){
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path, fileName);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            if(bm.compress(Bitmap.CompressFormat.PNG, 100, bos)) {
                bos.flush();
                bos.close();
            }
            else
            {
                throw new Exception("保存Bitmpap失败!");
            }
        } catch (Exception e) {
            Log.e("异常201511171002",e.toString());
        }
    }

    /**
     * 获取图片文件类型
     */
    public static String getImageType(String path)
    {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true; //确保图片不加载到内存
        BitmapFactory.decodeFile(path,opts);
        return opts.outMimeType;
    }
    /**
     * <br>功能简述:4.4及以上获取图片的方法
     * <br>功能详细描述:
     * <br>注意:
     * @param context
     * @param uri
     * @return
     */
//    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


}
