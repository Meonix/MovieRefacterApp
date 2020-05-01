package com.mionix.myapplication.repo

import com.mionix.myapplication.api.Api
import com.mionix.myapplication.base.Response
import com.mionix.myapplication.base.ResponseError
import com.mionix.myapplication.model.CastAndCrew

class CastAndCrewRepo (private val mApi: Api){

    suspend fun getCastAndCrew(movieId: Int): Response<CastAndCrew> {
//       Response.loading(null)
        return try {
            Response.success(mApi.getCastAndCrew(movieId))
        } catch (ex:Exception) {
            Response.error(ResponseError(101,ex.message.toString()))
        }
    }

}