package com.brayskiy.example.rest

import com.brayskiy.example.models.MovieDetails
import com.brayskiy.example.models.MoviesData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MobileService {
    @GET("popular")
    fun listPopular(@Query("api_key") api_key: String, @Query("page") page: Int?): Observable<MoviesData>

    @GET("top_rated")
    fun listTopRated(@Query("api_key") api_key: String, @Query("page") page: Int?): Observable<MoviesData>

    @GET("{movie_id}")
    fun getMovieDetails(@Path("movie_id") movie_id: Int?, @Query("api_key") api_key: String,
                        @Query("append_to_response") appendage: String)
            : Observable<MovieDetails>
}
