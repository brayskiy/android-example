package com.brayskiy.example.card

import com.brayskiy.example.models.MovieSummary

/**
 * Created by brayskiy on 01/31/19.
 */

interface MovieSummaryHandler {
    fun onCardClicked(position: Int, movieSummary: MovieSummary)
}
