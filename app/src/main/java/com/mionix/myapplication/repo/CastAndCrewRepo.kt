package com.mionix.myapplication.repo

import com.mionix.myapplication.api.Api
import com.mionix.myapplication.base.Response
import com.mionix.myapplication.base.ResponseError
import com.mionix.myapplication.model.CastAndCrew

class CastAndCrewRepo (private val mApi: Api){

    suspend fun getCastAndCrew(movie_id: Int): Response<CastAndCrew> {
//       Response.loading(null)
        return try {
            Response.success(mApi.getCastAndCrew(movie_id))
        } catch (ex:Exception) {
            Response.error(ResponseError(101,ex.message.toString()))
        }
    }

}