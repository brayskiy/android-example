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
import com.brayskiy.example.models.MovieDetails
import com.brayskiy.example.rest.Keys
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

interface MovieDetailsActivityContract {
    interface IHandler : IContract.Handler<IAdapter> {
        fun getMovieDetails(id: Int)
    }

    interface IAdapter : IContract.Adapter {
        fun onMovieDetailsSuccess(movieDetails: MovieDetails)
        fun onMovieDetailsError(throwable: Throwable)
    }

    class Handler @Inject internal constructor(private val application: Application,
                                               private val sharedPreferences: SharedPreferences,
                                               private val mobileService: MobileService,
                                               @IoSched private val ioScheduler: Scheduler,
                                               @UiSched private val uiScheduler: Scheduler) : BaseHandler<IAdapter>(), IHandler {

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            Log.i("Grant Results: " + grantResults.size)

        }

        override fun getMovieDetails(id: Int) {
            val unused = mobileService.getMovieDetails(id, Keys.keyFor(Keys.Types.the_movie_db), "videos")
                .observeOn(uiScheduler)
                .subscribeOn(ioScheduler)
                .subscribe ({ movieDetails ->
                    adapter?.onMovieDetailsSuccess(movieDetails)
                }, { throwable ->
                    adapter?.onMovieDetailsError(throwable)
                })
        }
    }
}
