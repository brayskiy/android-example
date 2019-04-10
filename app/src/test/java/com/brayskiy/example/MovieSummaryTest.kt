package com.brayskiy.example

import com.brayskiy.example.models.MovieSummary

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import org.mockito.Mockito.*

/**
 * Created by brayskiy on 04/05/19.
 */

class MovieSummaryTest {

    @Mock lateinit var moviesData: MutableList<MovieSummary>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_movieSummary() {
        val summary = getMovieSummary()

        moviesData.add(summary)
        verify(moviesData).add(summary)
    }


    fun getMovieSummary(): MovieSummary  {
       return MovieSummary(100, 12345, true, 0.5, "Title", 0.8,
           "poster path", "en", "Original Title", listOf(1, 2, 3),
           "backdrop path", false, "Movie overview", "Mar 12, 2012")
    }
}
