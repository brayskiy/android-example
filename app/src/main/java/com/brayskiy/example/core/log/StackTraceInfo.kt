package com.brayskiy.example.core.log

/**
 * Created by brayskiy on 02/01/19.
 */

object StackTraceInfo {
    private val CLIENT_CODE_STACK_INDEX: Int

    private val STACK_OFFSET = 3

    // + getInvokingClassName(STACK_OFFSET) + "/" +
    //return (tag.length() > 23) ? tag.substring(0, 22) : tag;
    val tag: String
        get() = (getInvokingFileName(STACK_OFFSET)
                + " [" + getInvokingMethodName(STACK_OFFSET) + "()]: ")

    init {
        var i = 0
        for (ste in Thread.currentThread().stackTrace) {
            i++
            if (ste.className == StackTraceInfo::class.java.name) {
                break
            }
        }
        CLIENT_CODE_STACK_INDEX = i
    }

    private fun getCurrentMethodName(offset: Int): String {
        return Thread.currentThread()
            .stackTrace[CLIENT_CODE_STACK_INDEX + offset].methodName
    }

    private fun getCurrentClassName(offset: Int): String {
        return Thread.currentThread()
            .stackTrace[CLIENT_CODE_STACK_INDEX + offset].className
    }

    private fun getCurrentFileName(offset: Int): String {
        val filename = Thread.currentThread()
            .stackTrace[CLIENT_CODE_STACK_INDEX + offset].fileName
        val lineNumber = Thread.currentThread()
            .stackTrace[CLIENT_CODE_STACK_INDEX + offset].lineNumber

        return "$filename:$lineNumber"
    }

    private fun getInvokingMethodName(offset: Int): String {
        return getCurrentMethodName(offset + 1)
    }

    private fun getInvokingClassName(offset: Int): String {
        return getCurrentClassName(offset + 1)
    }

    private fun getInvokingFileName(offset: Int): String {
        return getCurrentFileName(offset + 1)
    }

    private fun getCurrentMethodNameFqn(offset: Int): String {
        val currentClassName = getCurrentClassName(offset + 1)
        val currentMethodName = getCurrentMethodName(offset + 1)

        return "$currentClassName.$currentMethodName"
    }

    private fun getInvokingMethodNameFqn(offset: Int): String {
        val invokingClassName = getInvokingClassName(offset + 1)
        val invokingMethodName = getInvokingMethodName(offset + 1)

        return "$invokingClassName.$invokingMethodName"
    }

}
