package com.brayskiy.example.card

import androidx.recyclerview.widget.RecyclerView

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.brayskiy.example.R
import com.brayskiy.example.card.base.BaseCard
import com.brayskiy.example.card.base.BaseViewHolder
import com.brayskiy.example.card.base.CardHandlerProvider
import com.brayskiy.example.card.base.CardType
import com.brayskiy.example.dagger.ActivityComponent
import com.brayskiy.example.models.MovieSummary
import com.brayskiy.example.rest.BaseUrl
import com.squareup.picasso.Picasso

/**
 * Created by brayskiy on 01/31/19.
 */

class MovieSummaryCard(movieSummary: MovieSummary, private val picasso: Picasso, injector: ActivityComponent,
                       cardHandlerProvider: CardHandlerProvider<MovieSummaryHandler>)
    : BaseCard<MovieSummary, MovieSummaryHandler, MovieSummaryCard.ViewHolder>(R.layout.movie_summary, movieSummary, cardHandlerProvider) {

    override val cardType: CardType
        get() = CardType.POPULAR_MOVIES_ITEM

    init {
        injector.inject(this)
    }

    override fun onCreateViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun updateCardViews() {
        viewHolder?.movieTouchedId = cardData.id
        viewHolder?.title?.text = cardData.title
        viewHolder?.rating?.text = """Rating: ${cardData.voteAverage}"""

        picasso.load(BaseUrl.THE_MOVIE_DB_POSTER_URL + cardData.posterPath).into(viewHolder?.posterImage)

        viewHolder?.summaryContainer?.setOnClickListener(({ this.onCardClicked(it) }))
    }

    private fun onCardClicked(view: View) {
        cardHandler.onCardClicked(viewHolder!!.adapterPosition, cardData)
    }

    override fun onViewRecycled(viewHolder: RecyclerView.ViewHolder) {
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        var movieTouchedId: Int = 0

        val summaryContainer: RelativeLayout = itemView.findViewById(R.id.summary_container)
        val posterImage: ImageView = itemView.findViewById(R.id.summary_poster_image)
        val title: TextView = itemView.findViewById(R.id.summary_title)
        val rating: TextView = itemView.findViewById(R.id.summary_rating)
    }
}
