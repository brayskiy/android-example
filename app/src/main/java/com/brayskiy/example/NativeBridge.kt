package com.brayskiy.example

object NativeBridge {
    init {
        System.loadLibrary("native-lib")
    }

    external fun stringFromJNI(): String
    external fun getUserName(): String
    external fun getUserPassword(): String
}
