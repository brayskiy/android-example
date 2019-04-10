package com.brayskiy.example

import android.content.Context
import android.support.multidex.MultiDex
import com.brayskiy.example.base.BaseApplication
import com.brayskiy.example.core.log.Log
import com.brayskiy.example.dagger.*

/**
 * Created by brayskiy on 01/31/19.
 */

class App : BaseApplication() {
    private var appComponent: AppComponent? = null
    private var activityComponent: ActivityComponent? = null

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()

        getAppComponent().inject(this)

        Log.init(true, Log.LoggerType.TIMBER)
    }

    fun getAppComponent(): AppComponent {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule(this))
                .build()
        }
        return appComponent!!
    }

    fun getActivityComponent(): ActivityComponent {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                .appComponent(getAppComponent())
                .build()
        }
        return activityComponent!!
    }
}
