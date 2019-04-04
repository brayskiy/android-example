package com.brayskiy.example.dagger

import android.app.Application
import android.content.Context
import com.brayskiy.example.contract.*
import dagger.Module
import dagger.Provides

/**
 * Created by brayskiy on 10/27/17.
 */

@Module
class ActivityModule {
    @Provides
    @PerActivity
    fun getContext(application: Application): Context {
        return application
    }

    @Provides
    @PerActivity
    internal fun getMainActivityHandler(handler: MainActivityContract.Handler): MainActivityContract.IHandler {
        return handler
    }

    @Provides
    fun getMovieDetailsContract(fragment: MovieDetailsActivityContract.Handler):
            MovieDetailsActivityContract.IHandler {
        return fragment
    }

    @Provides
    @PerActivity
    internal fun getSettingsActivityHandler(handler: SettingsActivityContract.Handler): SettingsActivityContract.IHandler {
        return handler
    }

    @Provides
    fun getPopularMoviesFragmentContract(fragment: PopularMoviesFragmentContract.FragmentHandler):
            PopularMoviesFragmentContract.IFragmentHandler {
        return fragment
    }

    @Provides
    fun getTopRatedMoviesFragmentContract(fragment: TopRatedMoviesFragmentContract.FragmentHandler):
            TopRatedMoviesFragmentContract.IFragmentHandler {
        return fragment
    }
}
