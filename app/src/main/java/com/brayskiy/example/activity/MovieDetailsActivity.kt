package com.brayskiy.example.activity

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.brayskiy.example.R
import com.brayskiy.example.base.BaseActivity
import com.brayskiy.example.contract.MovieDetailsActivityContract
import com.brayskiy.example.core.model.DialogData
import com.brayskiy.example.dagger.ActivityComponent
import com.brayskiy.example.models.MovieDetails
import com.brayskiy.example.rest.BaseUrl
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Created by brayskiy on 01/31/19.
 */

class MovieDetailsActivity : BaseActivity<MovieDetailsActivityContract.IAdapter, MovieDetailsActivityContract.IHandler>(),
    MovieDetailsActivityContract.IAdapter {

    override var layoutId = R.layout.activity_movie_details

    lateinit var picasso: Picasso @Inject set

    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var duration: TextView
    private lateinit var rating: TextView
    private lateinit var synopsis: TextView
    private lateinit var videoButton: Button

    override fun setupActivity(activityComponent: ActivityComponent, savedInstanceState: Bundle?) {
        activityComponent.inject(this)

        poster = findViewById(R.id.movie_details_poster_image)
        title = findViewById(R.id.movie_details_title)
        duration = findViewById(R.id.movie_details_duration)
        rating = findViewById(R.id.movie_details_rating)
        synopsis = findViewById(R.id.detail_synopsis)

        videoButton = findViewById(R.id.movie_details_video_btn)
        videoButton.setOnClickListener {
            val dialogData = DialogData(DialogData.Type.ALERT)
            dialogData.titleResId = R.string.not_implemented_title
            dialogData.messageResId =  R.string.not_implemented_message
            displayDialog(dialogData)
        }

        val movieId = intent.getIntExtra(MOVIE_ID, 0)
        handler.getMovieDetails(movieId)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)

        for (i in 0 until menu.size()) {
            val drawable = menu.getItem(i).icon
            if (drawable != null) {
                drawable.mutate()
                drawable.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.home_menu_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }

            R.id.menu_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }

            R.id.menu_account -> {
                val dialogData = DialogData(DialogData.Type.ALERT)
                dialogData.titleResId = R.string.not_implemented_title
                dialogData.messageResId =  R.string.not_implemented_message
                displayDialog(dialogData)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMovieDetailsSuccess(movieDetails: MovieDetails) {
        title.text = movieDetails.title
        rating.text = String.format("Rating:  %s", movieDetails.voteAverage.toString())
        duration.text = String.format("Duration:  %s minutes", movieDetails.runtime.toString())
        synopsis.text ="Synopsis:\n\n" + movieDetails.overview

        val url = BaseUrl.THE_MOVIE_DB_POSTER_URL + movieDetails.posterPath
        picasso.load(url).into(poster)
    }

    override fun onMovieDetailsError(throwable: Throwable) {
        val dialogData = DialogData(DialogData.Type.ALERT)
        dialogData.titleResId = R.string.error_title
        dialogData.messageResId =  R.string.error_movie_details
        dialogData.positiveCallback = Runnable { finish() }
        displayDialog(dialogData)
    }

    companion object {
        const val MOVIE_ID = "movie_id"
    }
}
