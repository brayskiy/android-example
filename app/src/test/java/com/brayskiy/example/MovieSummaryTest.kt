package com.brayskiy.example

import com.brayskiy.example.models.MovieSummary
import org.junit.Assert.assertEquals

import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

import org.mockito.Mockito.*
import org.mockito.Spy

/**
 * Created by brayskiy on 04/05/19.
 */

class MovieSummaryTest {

    @Spy val moviesData: MutableList<MovieSummary> = mutableListOf()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_movieSummary() {
        val summary = getMovieSummary()

        moviesData.add(summary)
        verify(moviesData).add(summary)

        assertEquals(1, moviesData.size)
        assertEquals(0.5, moviesData[0].voteAverage, 0.0001)
        assertEquals("Title", moviesData[0].title)
    }


    fun getMovieSummary(): MovieSummary  {
       return MovieSummary(100, 12345, true, 0.5, "Title", 0.8,
           "poster path", "en", "Original Title", listOf(1, 2, 3),
           "backdrop path", false, "Movie overview", "Mar 12, 2012")
    }
}
