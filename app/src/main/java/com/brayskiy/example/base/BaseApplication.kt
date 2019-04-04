package com.brayskiy.example.base

import android.content.Context
import android.support.multidex.MultiDex
import androidx.multidex.MultiDexApplication

/**
 * Created by brayskiy on 01/31/19.
 */

open class BaseApplication : MultiDexApplication() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
