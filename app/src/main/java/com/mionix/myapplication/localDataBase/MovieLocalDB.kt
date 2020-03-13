package com.mionix.myapplication.localDataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = [FavouritesTable::class,WatchListTable::class],version = 2)
abstract class MovieLocalDB : RoomDatabase() {
    abstract fun movieDAO(): MovieDao
}