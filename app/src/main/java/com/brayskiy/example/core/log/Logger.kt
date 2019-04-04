package com.brayskiy.example.core.log

/**
 * Created by brayskiy on 02/01/19.
 */

interface Logger {
    fun e(throwable: Throwable)
    fun e(msg: String?)
    fun e(msg: String?, vararg args: Any)
    fun w(throwable: Throwable)
    fun w(msg: String?)
    fun w(msg: String?, vararg args: Any)
    fun i(msg: String?)
    fun i(msg: String?, vararg args: Any)
    fun d(msg: String?)
    fun d(msg: String?, vararg args: Any)
    fun v(msg: String?)
    fun v(msg: String?, vararg args: Any)
}
