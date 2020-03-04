package com.mionix.myapplication.api

import com.mionix.myapplication.model.Movie
import retrofit2.http.GET

interface Api {
    @GET("movie/76341?api_key=15c533c508cc3b45ff37b8880b31edfe")
    suspend fun getMovie(): Movie
}