package com.test.tool;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by John on 2015/12/2.
 */
public class TimeTool {
    public static long getTime()
    {
        Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
        return now.getTime();
    }

    public static String getNow()
    {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }
}
