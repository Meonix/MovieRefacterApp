package com.mionix.myapplication.localDataBase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.mionix.myapplication.model.Movie
import com.mionix.myapplication.model.LocalSavedMovie

val DATABASE_NAME = "myDB"
val TABLE_NAME = "FavouritesTable"
val COL_TITLE = "title"
val COL_ID = "id"
val COL_MOVIE_ID = "movieid"
val COL_POSTER = "posterPath"
val COL_OVERVIEW = "overview"
val COL_DATE_SAVE = "dateSave"

val WATCH_LIST_TABLE_NAME = "WatchListTable"
val WATCH_LIST_COL_TITLE = "title"
val WATCH_LIST_COL_ID = "id"
val WATCH_LIST_COL_MOVIE_ID = "movieid"
val WATCH_LIST_COL_POSTER = "posterPath"
val WATCH_LIST_COL_OVERVIEW = "overview"
val WATCH_LIST_COL_DATE_SAVE = "dateSave"
class MovieLocalDatabase (var context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
            val createTableFavourite = "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_MOVIE_ID + " INTEGER," +
                    COL_POSTER + " TEXT," +
                    COL_OVERVIEW + " TEXT," +
                    COL_DATE_SAVE + " TEXT," +
                    COL_TITLE + " TEXT)"
            db?.execSQL(createTableFavourite)
            val createTableWatchList = "CREATE TABLE " + WATCH_LIST_TABLE_NAME + " (" +
                    WATCH_LIST_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    WATCH_LIST_COL_MOVIE_ID + " INTEGER," +
                    WATCH_LIST_COL_POSTER + " TEXT," +
                    WATCH_LIST_COL_OVERVIEW + " TEXT," +
                    WATCH_LIST_COL_DATE_SAVE + " TEXT," +
                    WATCH_LIST_COL_TITLE + " TEXT)"
            db?.execSQL(createTableWatchList)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
    fun insertFavouriteListData (list_movie: Movie,dateSave : String){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_OVERVIEW,list_movie.overview)
        cv.put(COL_POSTER,list_movie.poster_path)
        cv.put(COL_TITLE,list_movie.title)
        cv.put(COL_MOVIE_ID,list_movie.id)
        cv.put(COL_DATE_SAVE,dateSave)
        val result = db.insert(TABLE_NAME,null,cv)
        if(result == (-1).toLong()){
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"Favourites Movie was Added to Database", Toast.LENGTH_SHORT).show()
        }
    }
    fun insertWatchListData (list_movie: Movie,dateSave : String){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(WATCH_LIST_COL_OVERVIEW,list_movie.overview)
        cv.put(WATCH_LIST_COL_POSTER,list_movie.poster_path)
        cv.put(WATCH_LIST_COL_TITLE,list_movie.title)
        cv.put(WATCH_LIST_COL_MOVIE_ID,list_movie.id)
        cv.put(WATCH_LIST_COL_DATE_SAVE,dateSave)
        val result = db.insert(WATCH_LIST_TABLE_NAME,null,cv)
        if(result == (-1).toLong()){
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"WatchList Movie was Added to Database", Toast.LENGTH_SHORT).show()
        }
    }

 //   @SuppressLint("Recycle")
//    fun count():Int{
//        val db = this.readableDatabase
//        val query = "Select * from " + TABLE_NAME
//        val result = db.rawQuery(query,null)
//        return result.count
//    }
    fun readFavouriteListData() : MutableList<LocalSavedMovie>{
        val list : MutableList<LocalSavedMovie> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                val movie = LocalSavedMovie()
                movie.title = result.getString(result.getColumnIndex(COL_TITLE))
                movie.poster = result.getString(result.getColumnIndex(COL_POSTER))
                movie.overview = result.getString(result.getColumnIndex(COL_OVERVIEW))
                movie.id = result.getString(result.getColumnIndex(COL_MOVIE_ID)).toInt()
                movie.datesave = result.getString(result.getColumnIndex(COL_DATE_SAVE))
                list.add(movie)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun readWatchListData() : MutableList<LocalSavedMovie>{
        val list : MutableList<LocalSavedMovie> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from " + WATCH_LIST_TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                val movie = LocalSavedMovie()
                movie.title = result.getString(result.getColumnIndex(WATCH_LIST_COL_TITLE))
                movie.poster = result.getString(result.getColumnIndex(WATCH_LIST_COL_POSTER))
                movie.overview = result.getString(result.getColumnIndex(WATCH_LIST_COL_OVERVIEW))
                movie.id = result.getString(result.getColumnIndex(WATCH_LIST_COL_MOVIE_ID)).toInt()
                movie.datesave = result.getString(result.getColumnIndex(WATCH_LIST_COL_DATE_SAVE))
                list.add(movie)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }
}