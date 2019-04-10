package com.brayskiy.example.fragment

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brayskiy.example.R
import com.brayskiy.example.activity.MovieDetailsActivity
import com.brayskiy.example.base.BaseFragment
import com.brayskiy.example.card.MovieSummaryHandler
import com.brayskiy.example.card.MovieSummaryCard
import com.brayskiy.example.card.base.BaseCard
import com.brayskiy.example.card.base.CardHandlerProvider
import com.brayskiy.example.card.base.CardsViewAdapter
import com.brayskiy.example.contract.PopularMoviesFragmentContract
import com.brayskiy.example.core.log.Log
import com.brayskiy.example.core.model.DialogData
import com.brayskiy.example.dagger.ActivityComponent
import com.brayskiy.example.models.MovieSummary
import com.brayskiy.example.models.MoviesData
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Created by brayskiy on 01/31/19.
 */

class PopularMoviesFragment : BaseFragment<PopularMoviesFragmentContract.IFragmentAdapter,
        PopularMoviesFragmentContract.IFragmentHandler>(), PopularMoviesFragmentContract.IFragmentAdapter {

    lateinit var picasso: Picasso @Inject set

    protected lateinit var injector: ActivityComponent @Inject set

    override val layoutId: Int = R.layout.popular_movies_fragment

    private lateinit var popularMoviesRecycler: RecyclerView

    private var topRatedMax = Integer.MAX_VALUE

    private var loading = false
    private var currentPage = 0

    override fun setupFragmentView(view: View) {
        popularMoviesRecycler = view.findViewById(R.id.popular_movies_recycler)

        popularMoviesRecycler.layoutManager = GridLayoutManager(context, 2)
        popularMoviesRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    if (!loading && currentPage < topRatedMax) {
                        handler.popularMovies(++currentPage)
                        loading = true
                    } else {
                        loading = false
                    }
                } else if (dy < 0) {
                    if (!loading && currentPage >= 1) {
                        if (currentPage == 1) {
                            handler.popularMovies(currentPage)
                        } else {
                            handler.popularMovies(--currentPage)
                        }
                        loading = true
                    } else {
                        loading = false
                    }
                }
            }
        })
    }

    override fun injectFragment(injector: ActivityComponent) {
        this.injector = injector
        injector.inject(this)

        handler.onAddViewAdapter(this)

        handler.bindData().observe(this, Observer { data ->
            // TODO
        })
    }

    override fun onPopularSuccess(moviesData: MoviesData, provider: CardHandlerProvider<MovieSummaryHandler>) {
        if (moviesData.results.isEmpty()) {
            val dialogData = DialogData(DialogData.Type.SNACKBAR)
            dialogData.message = "Sadly the popular movies are not available.  Please try again."
            displayDialog(dialogData)
        } else {
            Log.d("results size = " + moviesData.results.size)
            topRatedMax = moviesData.totalPages

            val cardsList = ArrayList<BaseCard<*, *, RecyclerView.ViewHolder>>()
            moviesData.results.forEach {
                val card = MovieSummaryCard(it, picasso, injector, provider)
                cardsList.add(card as BaseCard<*, *, RecyclerView.ViewHolder>)
            }

            if (popularMoviesRecycler.adapter == null) {
                popularMoviesRecycler.adapter = CardsViewAdapter(context!!, cardsList)
            } else {
                (popularMoviesRecycler.adapter as CardsViewAdapter).addCardsList(cardsList)
            }
        }
    }

    override fun onPopularError(throwable: Throwable) {
        val dialogData = DialogData(DialogData.Type.ALERT)
        dialogData.titleResId = R.string.error_title
        dialogData.messageResId =  R.string.error_content
        dialogData.positiveButtonResId = R.string.try_again
        dialogData.positiveCallback = Runnable { handler.popularMovies(1) }
        dialogData.negativeButtonResId = R.string.exit
        dialogData.negativeCallback = Runnable { activity?.finish() }
        displayDialog(dialogData)
    }

    override fun onCardClicked(movieSummary: MovieSummary) {
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra(MovieDetailsActivity.MOVIE_SUMMARY, movieSummary)
        intent.putExtra(MovieDetailsActivity.MOVIE_ID, movieSummary.id)
        startActivity(intent)
    }

    companion object {

        @JvmStatic
        fun newInstance(): PopularMoviesFragment {
            return PopularMoviesFragment()
        }
    }
}
