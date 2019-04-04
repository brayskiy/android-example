package com.brayskiy.example.contract

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import com.brayskiy.example.NativeBridge.stringFromJNI
import com.brayskiy.example.activity.MainActivity
import com.brayskiy.example.base.BaseHandler
import com.brayskiy.example.base.IContract
import com.brayskiy.example.core.log.Log
import com.brayskiy.example.dagger.IoSched
import com.brayskiy.example.dagger.UiSched
import com.brayskiy.example.rest.MobileService
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by brayskiy on 01/31/19.
 */

interface MainActivityContract {
    interface IHandler : IContract.Handler<IAdapter> {
        fun getActivityTitle()
    }

    interface IAdapter : IContract.Adapter {
        fun setActivityTitle(title: String)
    }

    class Handler @Inject internal constructor(private val application: Application,
                                               private val sharedPreferences: SharedPreferences,
                                               private val mobileService: MobileService,
                                               private val gson: Gson,
                                               @IoSched private val ioScheduler: Scheduler,
                                               @UiSched private val uiScheduler: Scheduler) : BaseHandler<IAdapter>(), IHandler {

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            Log.i("Grant Results: " + grantResults.size)

            var allGranted = true
            for (ind in grantResults.indices) {
                if (grantResults[ind] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Permission " + permissions[ind] + " is granted")
                } else {
                    Log.d("Permission " + permissions[ind] + " is NOT granted")
                    allGranted = false
                }
            }

            if (allGranted) {
                when (requestCode) {
                    MainActivity.REQUEST_PERMISSIONS -> {
                        Log.i("All of the required permissions are granted.")
                    }

                    else -> {
                        Log.w("Not all of the required permissions are granted.")
                    }
                }
            }
        }

        override fun getActivityTitle() {
            Observable.just(1)
                .flatMap { Observable.just(stringFromJNI()) }
                .observeOn(uiScheduler)
                .subscribeOn(ioScheduler)
                .subscribe(object: Observer<String> {
                    override fun onComplete() {}

                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(title: String) {
                        adapter?.setActivityTitle(title)
                    }

                    override fun onError(e: Throwable) {
                        // NOTHING
                    }
                })
        }
    }
}
