package com.brayskiy.example.base

import android.app.Activity
import android.os.Bundle

/**
 * Created by brayskiy on 01/31/19.
 */

abstract class BaseHandler<A : IContract.Adapter> : IContract.Handler<A>, BaseActionHandler() {

    @JvmField
    var adapter: A? = null

    @JvmField
    var activity: Activity? = null

    override fun onAddViewAdapter(adapter: A, extras: Bundle?) {
        this.adapter = adapter
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle) {
        this.activity = activity
    }

    override fun onActivityStarted(activity: Activity) {
        this.activity = activity
    }

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        this.adapter = null
        this.activity = null
    }
}
