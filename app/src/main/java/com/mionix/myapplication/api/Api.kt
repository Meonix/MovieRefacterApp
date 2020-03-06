package com.mionix.myapplication.api

import com.mionix.myapplication.model.ListMovie
import com.mionix.myapplication.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query
const val POSTER_BASE_URL = "http://image.tmdb.org/t/p/w500"
interface Api {
    @GET("movie/popular?&language=en-US")
    suspend fun getPoppularMovie(@Query("page") page: Int): ListMovie
}