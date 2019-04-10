package com.brayskiy.example

/**
 * Created by brayskiy on 01/31/19.
 */

object NativeBridge {
    init {
        System.loadLibrary("native-lib")
    }

    external fun stringFromJNI(): String
    external fun getUserName(): String
    external fun getUserPassword(): String
}
