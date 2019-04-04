package com.brayskiy.example.core.log

/**
 * Created by brayskiy on 02/01/19.
 */

object Log {
    private lateinit var mLogger: Logger

    private var isEnabled = false

    enum class LoggerType {
        ANDROID,
        TIMBER
    }

    fun init(enable: Boolean, loggerType: LoggerType) {
        if (enable) {
            when (loggerType) {
                Log.LoggerType.TIMBER -> {
                    mLogger = TimberLogger()
                }

                Log.LoggerType.ANDROID -> {
                    mLogger = AndroidLogger()
                }
            }
        }

        isEnabled = enable
    }

    fun e(msg: String) {
        if (isEnabled) mLogger.e(msg)
    }

    fun e(throwable: Throwable) {
        if (isEnabled) mLogger.e(throwable)
    }

    fun e(msg: String, vararg args: Any) {
        if (isEnabled) mLogger.e(msg, *args)
    }

    fun w(msg: String) {
        if (isEnabled) mLogger.w(msg)
    }

    fun w(throwable: Throwable) {
        if (isEnabled) mLogger.w(throwable)
    }

    fun w(msg: String, vararg args: Any) {
        if (isEnabled) mLogger.w(msg, *args)
    }

    fun i(msg: String) {
        if (isEnabled) mLogger.i(msg)
    }

    fun i(msg: String, vararg args: Any) {
        if (isEnabled) mLogger.i(msg, *args)
    }

    fun d(msg: String?) {
        if (isEnabled) mLogger.d(msg)
    }

    fun d(msg: String?, vararg args: Any) {
        if (isEnabled) mLogger.d(msg, *args)
    }

    fun v(msg: String?) {
        if (isEnabled) mLogger.v(msg)
    }

    fun v(msg: String?, vararg args: Any) {
        if (isEnabled) mLogger.v(msg, *args)
    }
}
