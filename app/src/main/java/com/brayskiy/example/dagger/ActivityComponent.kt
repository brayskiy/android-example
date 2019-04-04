package com.brayskiy.example.dagger

import com.brayskiy.example.activity.MainActivity
import com.brayskiy.example.activity.MovieDetailsActivity
import com.brayskiy.example.activity.SettingsActivity
import com.brayskiy.example.card.HostCard
import com.brayskiy.example.card.MovieSummaryCard
import com.brayskiy.example.fragment.PopularMoviesFragment
import com.brayskiy.example.fragment.TopRatedMoviesFragment
import dagger.Component

/**
 * Created by brayskiy on 10/27/17.
 */

@PerActivity
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: MovieDetailsActivity)
    fun inject(activity: SettingsActivity)

    fun inject(fragment: PopularMoviesFragment)
    fun inject(fragment: TopRatedMoviesFragment)

    fun inject(hostCard: HostCard)
    fun inject(movieSummaryCard: MovieSummaryCard)
}
