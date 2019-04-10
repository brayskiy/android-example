#include <jni.h>
#include <string>

/**
 * Created by brayskiy on 01/31/19.
 */

extern "C" JNIEXPORT jstring JNICALL
Java_com_brayskiy_example_NativeBridge_stringFromJNI(JNIEnv *env, jobject /* this */) {
    std::string hello = "Boris Example";
    return env->NewStringUTF(hello.c_str());
}
