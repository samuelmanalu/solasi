//
// Created by Samuel Manalu on 1/1/21
//

#include <jni.h>
#include <string.h>
#include <iostream>

extern "C" JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_samuel_solasi_util_JniUtil_Method(
        JNIEnv* env,
        jclass clazz) {
    std::string hello = "Created By: Samuel - 1606889811";
    return env->NewStringUTF(hello.c_str());
}
