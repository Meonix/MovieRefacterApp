package com.mionix.myapplication.localDataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert
    fun saveFavouriesMovie(movie : FavouritesTable)
    @Query("select * from FavouritesTable WHERE colMovieID = :colMovieID")
    fun readFavouriesMovie(colMovieID : Int) :List<FavouritesTable>
    @Query("select * from FavouritesTable")
    fun readAllFavouriesMovie() :List<FavouritesTable>


    @Insert
    fun saveWatchListTable(movie : WatchListTable)
    @Query("select * from WatchListTable WHERE colMovieID = :colMovieID")
    fun readWatchListTable(colMovieID : Int) :List<WatchListTable>
    @Query("select * from WatchListTable")
    fun readAllWatchListTable() :List<WatchListTable>

}