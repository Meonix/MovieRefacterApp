package com.mionix.myapplication.view.fragment


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth

import com.mionix.myapplication.R
import com.mionix.myapplication.localDataBase.FavouritesTable
import com.mionix.myapplication.localDataBase.MovieLocalDB
import com.mionix.myapplication.localDataBase.WatchListTable
import com.mionix.myapplication.model.LocalSavedMovie
import com.mionix.myapplication.view.adapter.FavouriesMovieAdapter
import com.mionix.myapplication.view.adapter.WatchListMovieAdapter
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_library.*

/**
 * A simple [Fragment] subclass.
 */
class LibraryFragment : Fragment() {
    private lateinit var watchListGridLayoutManager: GridLayoutManager
    private lateinit var favouritesListGridLayoutManager: GridLayoutManager
    private var listFavouriesMovie : MutableList<FavouritesTable> = mutableListOf()
    private var watchListMovie : MutableList<WatchListTable> = mutableListOf()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var adapterWatchListMovieView : WatchListMovieAdapter
    private lateinit var adapterFavouriesMovieView : FavouriesMovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val libraryFragment= inflater.inflate(R.layout.fragment_library, container, false)

        return libraryFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initData() {
        val db = context?.let {
            Room.databaseBuilder(
                it
                , MovieLocalDB::class.java
                ,"MyMovieDB")
                .fallbackToDestructiveMigration()
                .build()
        }
        Thread{
                val favouritesMovieData = db?.movieDAO()?.readAllFavouriesMovie()
                listFavouriesMovie.clear()
            favouritesMovieData?.let { listFavouriesMovie.addAll(it) }
                adapterFavouriesMovieView = FavouriesMovieAdapter(
                    listFavouriesMovie,context)
            activity?.runOnUiThread(Runnable {
                rvFavouritesMovie.adapter = adapterFavouriesMovieView
                rvFavouritesMovie.adapter?.notifyDataSetChanged()
            })
        }.start()

        Thread{
            val watchMovieData = db?.movieDAO()?.readAllWatchListTable()
            watchListMovie.clear()
            watchMovieData?.let { watchListMovie.addAll(it) }
            adapterWatchListMovieView = WatchListMovieAdapter(
                watchListMovie,context)
            activity?.runOnUiThread(Runnable {
                rvWatchListMovie.adapter = adapterWatchListMovieView
                rvWatchListMovie.adapter?.notifyDataSetChanged()
            })

        }.start()


    }

    private fun initView() {
        watchListGridLayoutManager = GridLayoutManager(context,3)
        watchListGridLayoutManager.orientation = GridLayoutManager.VERTICAL
        favouritesListGridLayoutManager = GridLayoutManager(context,3)
        favouritesListGridLayoutManager.orientation = GridLayoutManager.VERTICAL

        rvWatchListMovie.layoutManager = watchListGridLayoutManager
        rvWatchListMovie.isNestedScrollingEnabled = true
        rvWatchListMovie.setHasFixedSize(true)

        rvFavouritesMovie.layoutManager = favouritesListGridLayoutManager
        rvFavouritesMovie.isNestedScrollingEnabled = true
        rvFavouritesMovie.setHasFixedSize(true)


        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        if(currentUser != null ){
            llLibrary1.visibility = View.GONE
            llLibrary2.visibility = View.VISIBLE
        }
        else{
            llLibrary1.visibility = View.VISIBLE
            llLibrary2.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        initData()
    }
    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
