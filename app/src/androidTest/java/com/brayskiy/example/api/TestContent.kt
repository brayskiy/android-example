package com.brayskiy.example.api

import com.brayskiy.example.rest.BaseUrl
import com.brayskiy.example.rest.Keys
import com.brayskiy.example.rest.MobileService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CountDownLatch

/**
 * Created by brayskiy on 04/05/19.
 */

class TestContent {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BaseUrl.THE_MOVIE_DB_HOST_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(MobileService::class.java)

    @Test
    fun test_001_popularMovies() {
        val countDownLatch = CountDownLatch(1)

        val disposable = Observable.just(1)
            .flatMap { api.listTopRated(Keys.keyFor(Keys.Types.the_movie_db), it) }
            .mergeWith(api.listPopular(Keys.keyFor(Keys.Types.the_movie_db), 1))
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.trampoline())
            .subscribe ({ popular ->
                countDownLatch.countDown()
            }, { throwable ->
                countDownLatch.countDown()
                assert(false)
            })

        countDownLatch.await()

        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    @Test
    fun test_003_movieDetails() {
        val countDownLatch = CountDownLatch(1)

        val jackReacherMovieId = 343611
        val wonderWomanMovieId = 297762

        val disposable = api.getMovieDetails(jackReacherMovieId, Keys.keyFor(Keys.Types.the_movie_db), "videos")
            .flatMap { api.getMovieDetails(wonderWomanMovieId, Keys.keyFor(Keys.Types.the_movie_db), "videos") }
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.trampoline())
            .subscribe ({ popular ->
                countDownLatch.countDown()
            }, { throwable ->
                countDownLatch.countDown()
                assert(false)
            })

        countDownLatch.await()

        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }
}