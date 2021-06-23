//
// Created by rohit on 27-03-2021.
//

#include <jni.h>
#include <string>

std::string hello = "v8cebfbf744062c00aa85dabcc68c08d0c";


//extern "C" JNIEXPORT jstring JNICALL
//Java_com_vehiclecare_vc_driver_network_RetrofitClient_baseUrlFromJNI(JNIEnv *env, jclass clazz) {
//    std::string baseURL = "https://api.vehiclecare.in/";
//    return env->NewStringUTF(baseURL.c_str());
//}
////com.app.vehiclecare.ui.fragments.activity
//extern "C" JNIEXPORT jstring JNICALL
//Java_com_vehiclecare_vc_driver_activity_AccessCodeFromJNI(JNIEnv *env,jobject) {
//    return env->NewStringUTF(hello.c_str());
//}

//extern "C"
//JNIEXPORT jstring JNICALL
//Java_com_vehiclecare_vc_driver_app_network_RetrofitClient_baseUrlFromJNI(JNIEnv *env, jclass clazz) {
//    std::string baseURL = "https://api.vehiclecare.in/";
//    return env->NewStringUTF(baseURL.c_str());
//}extern "C"
//JNIEXPORT jstring JNICALL
//Java_com_vehiclecare_vc_driver_app_activity_BaseActivity_AccessCodeFromJNI(JNIEnv *env, jclass clazz) {
//    return env->NewStringUTF(hello.c_str());
//}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_vehiclecare_vc_1driver_1app_network_RetrofitClient_baseUrlFromJNI(JNIEnv *env,
                                                                           jclass clazz) {
    std::string baseURL = "https://api.vehiclecare.in/";
    return env->NewStringUTF(baseURL.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_vehiclecare_vc_1driver_1app_activity_BaseActivity_00024Companion_AccessCodeFromJNI(
        JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(hello.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_vehiclecare_vc_1driver_1app_activity_BaseActivity_AccessCodeFromJNI(JNIEnv *env,
                                                                             jclass clazz) {
    return env->NewStringUTF(hello.c_str());
}