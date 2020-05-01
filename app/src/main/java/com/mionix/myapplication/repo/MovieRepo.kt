package com.mionix.myapplication.repo

import com.mionix.myapplication.api.Api
import com.mionix.myapplication.base.Response
import com.mionix.myapplication.base.ResponseError
import com.mionix.myapplication.model.Movie

class MovieRepo (private val mApi: Api){

    suspend fun getMovie(movieId: Int): Response<Movie> {
//       Response.loading(null)
        return try {
            Response.success(mApi.getMovie(movieId))
        } catch (ex:Exception) {
            Response.error(ResponseError(101,ex.message.toString()))
        }
    }

}