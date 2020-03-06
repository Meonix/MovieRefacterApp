package com.mionix.myapplication.model


import com.google.gson.annotations.SerializedName

data class ListMovie(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)