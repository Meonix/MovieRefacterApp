package com.mionix.myapplication.localDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavouritesTable (@PrimaryKey
    val colMovieID: Int,
    @ColumnInfo (name = "title")
    val colTitle: String?,
    @ColumnInfo (name = "posterPath")
    val colPoster: String?,
    @ColumnInfo (name = "overview")
    val colOverview: String?,
    @ColumnInfo (name = "dateSave")
    val colDateSave: String?
    )
@Entity
class WatchListTable (
    @PrimaryKey
    val colMovieID: Int,
    @ColumnInfo (name = "title")
    val colTitle: String?,
    @ColumnInfo (name = "posterPath")
    val colPoster: String?,
    @ColumnInfo (name = "overview")
    val colOverview: String?,
    @ColumnInfo (name = "dateSave")
    val colDateSave: String?
)