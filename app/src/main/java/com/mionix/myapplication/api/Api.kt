package com.mionix.myapplication.api

import com.mionix.myapplication.model.CastAndCrew
import com.mionix.myapplication.model.ListMovie
import com.mionix.myapplication.model.Movie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
const val POSTER_BASE_URL = "http://image.tmdb.org/t/p/w500"
interface Api {
    @GET("movie/popular?&language=en-US")
    suspend fun getPoppularMovie(@Query("page") page: Int): ListMovie
    @GET("movie/top_rated?&language=en-US")
    suspend fun getTopRateMovie(@Query("page") page: Int): ListMovie
    @GET("movie/{movie_id}?&language=en-US")
    suspend fun getMovie(@Path("movie_id")movie_id: Int): Movie
    @GET("movie/{movie_id}/credits")
    suspend fun getCastAndCrew(@Path("movie_id")movie_id: Int): CastAndCrew
}