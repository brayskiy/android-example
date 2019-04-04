package com.brayskiy.example.core.log

import timber.log.Timber

/**
 * Created by brayskiy on 02/01/19.
 */

class TimberLogger : Logger {
    init {
        Timber.plant(Timber.DebugTree())
    }

    override fun e(throwable: Throwable) {
        Timber.e(throwable)
    }

    override fun e(msg: String?) {
        Timber.e(StackTraceInfo.tag + msg)
    }

    override fun e(msg: String?, vararg args: Any) {
        Timber.e(StackTraceInfo.tag + msg, *args)
    }

    override fun w(throwable: Throwable) {
        Timber.w(throwable)
    }

    override fun w(msg: String?) {
        Timber.w(StackTraceInfo.tag + msg)
    }

    override fun w(msg: String?, vararg args: Any) {
        Timber.w(StackTraceInfo.tag + msg, *args)
    }

    override fun i(msg: String?) {
        Timber.i(StackTraceInfo.tag + msg)
    }

    override fun i(msg: String?, vararg args: Any) {
        Timber.i(StackTraceInfo.tag + msg, *args)
    }

    override fun d(msg: String?) {
        Timber.d(StackTraceInfo.tag + msg)
    }

    override fun d(msg: String?, vararg args: Any) {
        Timber.d(StackTraceInfo.tag + msg, *args)
    }

    override fun v(msg: String?) {
        Timber.v(msg)
    }

    override fun v(msg: String?, vararg args: Any) {
        Timber.v(StackTraceInfo.tag + msg, *args)
    }
}
