#include "com_test_NdkJniUtils.h"

JNIEXPORT jstring JNICALL Java_com_test_NdkJniUtils_getStringFormJNI(JNIEnv *env, jobject obj){
    return (*env)->NewStringUTF(env,"This just a test for Android Studio NDK JNI developer!");
}