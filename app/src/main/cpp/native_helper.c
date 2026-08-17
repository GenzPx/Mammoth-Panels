/*
 * native_helper.c
 *
 * Native helper for Mammoth. Implemented in C to keep the hot paths (process
 * scanning, hashing, uptime arithmetic) off the garbage collector and to work
 * directly with Linux /proc — the kind of thing that belongs in C on Android.
 *
 * Built with the Android NDK via CMake (see CMakeLists.txt). Exposed to Kotlin
 * through JNI in dae.mammoth.id.nativelib.NativeHelper.
 */

#include <jni.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <ctype.h>
#include <android/log.h>

#define LOG_TAG "MammothNative"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

/* djb2 hash — used to fingerprint a string cheaply in C. */
static unsigned long hash_djb2(const char *str) {
    unsigned long hash = 5381;
    int c;
    while ((c = *str++)) {
        hash = ((hash << 5) + hash) + (unsigned char)c; /* hash * 33 + c */
    }
    return hash;
}

JNIEXPORT jstring JNICALL
Java_dae_mammoth_id_nativelib_NativeHelper_nativeVersion(JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env, "mammoth-native 1.0 (C/NDK)");
}

JNIEXPORT jstring JNICALL
Java_dae_mammoth_id_nativelib_NativeHelper_nativeArch(JNIEnv *env, jobject thiz) {
#if defined(__aarch64__)
    return (*env)->NewStringUTF(env, "arm64-v8a");
#elif defined(__arm__)
    return (*env)->NewStringUTF(env, "armeabi-v7a");
#elif defined(__x86_64__)
    return (*env)->NewStringUTF(env, "x86_64");
#else
    return (*env)->NewStringUTF(env, "unknown");
#endif
}

JNIEXPORT jlong JNICALL
Java_dae_mammoth_id_nativelib_NativeHelper_nativeHash(JNIEnv *env, jobject thiz,
                                                      jstring input) {
    if (input == NULL) return 0;
    const char *chars = (*env)->GetStringUTFChars(env, input, NULL);
    if (chars == NULL) return 0;
    jlong result = (jlong)hash_djb2(chars);
    (*env)->ReleaseStringUTFChars(env, input, chars);
    return result;
}

JNIEXPORT jint JNICALL
Java_dae_mammoth_id_nativelib_NativeHelper_nativeProcessAlive(JNIEnv *env, jobject thiz,
                                                              jint pid) {
    if (pid <= 1) return 0;
    char path[64];
    snprintf(path, sizeof(path), "/proc/%d/stat", (int)pid);
    FILE *f = fopen(path, "r");
    if (!f) return 0;
    /* process exists if we could open its stat file */
    fclose(f);
    return 1;
}
