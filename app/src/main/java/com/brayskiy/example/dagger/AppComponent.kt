package com.brayskiy.example.dagger

import android.app.Application
import android.content.SharedPreferences
import com.brayskiy.example.App
import com.brayskiy.example.rest.MobileService
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dagger.Component
import io.reactivex.Scheduler
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by brayskiy on 10/27/17.
 */

@PerApp
@Component(modules = [AppModule::class, ApiModule::class])
@Singleton
interface AppComponent {
    fun inject(application: App)

    fun getApplication(): Application

    fun getSharedPreferences(): SharedPreferences

    @IoSched
    fun getIOScheduler(): Scheduler

    @UiSched
    fun getUIScheduler(): Scheduler

    fun getMobileRetrofit(): Retrofit

    fun getMobileService(): MobileService

    fun getGson(): Gson

    fun getPicasso(): Picasso
}
