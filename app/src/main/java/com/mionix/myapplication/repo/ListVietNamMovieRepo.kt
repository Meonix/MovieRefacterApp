package com.mionix.myapplication.repo

import com.mionix.myapplication.api.Api
import com.mionix.myapplication.base.Response
import com.mionix.myapplication.base.ResponseError
import com.mionix.myapplication.model.ListMovie

class ListVietNamMovieRepo (private val mApi: Api) {
    suspend fun getVietNamMovie(): Response<ListMovie> {
        return try {
            Response.success(mApi.getVietNamMovie())
        } catch (ex:Exception) {
            Response.error(ResponseError(101,ex.message.toString()))
        }
    }
}