package com.brayskiy.example.contract


import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.brayskiy.example.NativeBridge
import com.brayskiy.example.base.BaseFragmentHandler
import com.brayskiy.example.base.IContract
import com.brayskiy.example.card.MovieSummaryHandler
import com.brayskiy.example.card.base.CardHandlerProvider
import com.brayskiy.example.dagger.IoSched
import com.brayskiy.example.dagger.UiSched
import com.brayskiy.example.models.MovieSummary
import com.brayskiy.example.models.MoviesData
import com.brayskiy.example.rest.BaseUrl
import com.brayskiy.example.rest.Keys
import com.brayskiy.example.rest.MobileService
import com.brayskiy.example.util.Network
import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.io.InputStreamReader
import java.util.*
import javax.inject.Inject

/**
 * Created by brayskiy on 01/31/19.
 */

class TopRatedMoviesFragmentContract {

    interface IFragmentHandler : IContract.FragmentHandler<IFragmentAdapter> {

        var adapter: IFragmentAdapter

        fun bindData(): MutableLiveData<String>
        fun popularMovies(page: Int)

        fun onAddViewAdapter(adapter: IFragmentAdapter) {
            this.adapter = adapter
        }
    }

    interface IFragmentAdapter : IContract.FragmentAdapter {
        fun onTopRatedSuccess(moviesData: MoviesData, provider: CardHandlerProvider<MovieSummaryHandler>)
        fun onTopRatedError(throwable: Throwable)
        fun onCardClicked(movieSummary: MovieSummary)
    }

    class FragmentHandler @Inject constructor(private val application: Application,
                                              private val sharedPreferences: SharedPreferences,
                                              private val mobileService: MobileService,
                                              @IoSched private val ioScheduler: Scheduler,
                                              @UiSched private val uiScheduler: Scheduler)
        : BaseFragmentHandler<IFragmentAdapter>(), IFragmentHandler, MovieSummaryHandler {

        override lateinit var adapter: IFragmentAdapter

        private val liveSshData: MutableLiveData<String> = MutableLiveData()
        override fun bindData(): MutableLiveData<String> = liveSshData

        override fun onFragmentAdded(fragmentAdapter: IFragmentAdapter, extras: Bundle?, savedInstanceState: Bundle?) {
            super.onFragmentAdded(fragmentAdapter, extras, savedInstanceState)
        }

        override fun onFragmentResumed() {
            super.onFragmentResumed()
            if (Network.isNetworkOnline(application)) {
                popularMovies(1)
            } else {
                adapter.onTopRatedError(Throwable("Network Error"))
            }
        }

        override fun onFragmentPaused() {
            super.onFragmentPaused()
        }

        override fun popularMovies(page: Int) {
            val unused = mobileService.listTopRated(Keys.keyFor(Keys.Types.the_movie_db), page)
                .observeOn(uiScheduler)
                .subscribeOn(ioScheduler)
                .subscribe(
                    { moviesData ->
                        adapter.onTopRatedSuccess(moviesData, provider)
                        //liveSshData.value = result
                    }, { throwable ->
                        adapter.onTopRatedError(throwable)
                    })
        }

        override fun onCardClicked(position: Int, movieSummary: MovieSummary) {
            adapter.onCardClicked(movieSummary)
        }

        private val provider: CardHandlerProvider<MovieSummaryHandler> = CardHandlerProvider(this@FragmentHandler)
    }
}
