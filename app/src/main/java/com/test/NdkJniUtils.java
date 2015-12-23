package com.test;

/**
 * Created by Administrator on 2015-12-22.
 */
public class NdkJniUtils {
    static {
        System.loadLibrary("YanboberJniLibName");   //defaultConfig.ndk.moduleName
    }
    public native String getStringFormJNI();
}
