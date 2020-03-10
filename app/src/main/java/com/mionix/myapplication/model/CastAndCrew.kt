package com.mionix.myapplication.model


import com.google.gson.annotations.SerializedName

data class CastAndCrew(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)