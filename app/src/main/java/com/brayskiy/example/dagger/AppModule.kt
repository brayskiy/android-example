package com.brayskiy.example.dagger

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.brayskiy.example.App
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton

/**
 * Created by brayskiy on 10/27/17.
 */

@Module
class AppModule(private val application: Application) {

    @Provides
    fun getApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun getSharedPreferences(): SharedPreferences {
        return application.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
    }

    @IoSched
    @Provides
    fun getIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    @UiSched
    @Provides
    fun getUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    val picasso: Picasso
        @Singleton
        @Provides
        get() {

            val cacheDir = File(application.cacheDir, "picasso-cache")

            if (!cacheDir.exists()) {
                cacheDir.mkdir()
            }

            val picassoClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("X-TOKEN", "VAL")
                        .build()
                    chain.proceed(newRequest)
                }
                .cache(Cache(cacheDir, PICASSO_DISK_CACHE_SIZE.toLong()))
                .build()

            return Picasso.Builder(application)
                .memoryCache(LruCache(PICASSO_MEMORY_CACHE_SIZE))
                .downloader(OkHttp3Downloader(picassoClient))
                .loggingEnabled(true)
                .build()
        }

    companion object {
        private const val PICASSO_DISK_CACHE_SIZE = 40 * 1024 * 1024
        private const val PICASSO_MEMORY_CACHE_SIZE = 20 * 1024 * 1024

        private const val APP_PREFS = "appPrefs"
    }
}
