package com.brayskiy.example.core.log

/**
 * Created by brayskiy on 02/01/19.
 */

class AndroidLogger : Logger {

    override fun e(throwable: Throwable) {
        android.util.Log.e(StackTraceInfo.tag, android.util.Log.getStackTraceString(throwable))
    }

    override fun e(msg: String?) {
        android.util.Log.e(StackTraceInfo.tag, msg)
    }

    override fun e(msg: String?, vararg args: Any) {
        android.util.Log.e(StackTraceInfo.tag, String.format(msg ?: "", *args))
    }

    override fun w(throwable: Throwable) {
        android.util.Log.w(StackTraceInfo.tag, android.util.Log.getStackTraceString(throwable))
    }

    override fun w(msg: String?) {
        android.util.Log.w(StackTraceInfo.tag, msg)
    }

    override fun w(msg: String?, vararg args: Any) {
        android.util.Log.w(StackTraceInfo.tag, String.format(msg ?: "", *args))
    }

    override fun i(msg: String?) {
        android.util.Log.i(StackTraceInfo.tag, msg)
    }

    override fun i(msg: String?, vararg args: Any) {
        android.util.Log.i(StackTraceInfo.tag, String.format(msg ?: "", *args))
    }

    override fun d(msg: String?) {
        android.util.Log.d(StackTraceInfo.tag, msg)
    }

    override fun d(msg: String?, vararg args: Any) {
        android.util.Log.d(StackTraceInfo.tag, String.format(msg ?: "", *args))
    }

    override fun v(msg: String?) {
        android.util.Log.v(StackTraceInfo.tag, msg)
    }

    override fun v(msg: String?, vararg args: Any) {
        android.util.Log.v(StackTraceInfo.tag, String.format(msg ?: "", *args))
    }
}
