package com.mionix.myapplication.repo

import com.mionix.myapplication.api.Api
import com.mionix.myapplication.base.Response
import com.mionix.myapplication.base.ResponseError
import com.mionix.myapplication.model.ListMovie
import com.mionix.myapplication.model.Movie

class ListPopularMovieRepo(private val mApi: Api) {
    suspend fun getPoppularMovie(page :Int): Response<ListMovie> {
        return try {
            Response.success(mApi.getPoppularMovie(page))
        } catch (ex:Exception) {
            Response.error(ResponseError(101,ex.message.toString()))
        }
    }
}