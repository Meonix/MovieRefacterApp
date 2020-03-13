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
import com.mionix.myapplication.localDataBase.MovieLocalDatabase
import com.mionix.myapplication.localDataBase.WatchListTable
import com.mionix.myapplication.model.LocalSavedMovie
import com.mionix.myapplication.view.adapter.FavouriesMovieAdapter
import com.mionix.myapplication.view.adapter.WatchListMovieAdapter

/**
 * A simple [Fragment] subclass.
 */
class LibraryFragment(context : Context, activity: Activity) : Fragment() {
    private var watchListGridLayoutManager: GridLayoutManager? = null
    private var favouritesListGridLayoutManager: GridLayoutManager? = null
    private var rvWatchListMovie :RecyclerView? = null
    private var rvFavouritesMovie :RecyclerView? = null
    private var llLibrary1 : LinearLayout? = null
    private var llLibrary2 : LinearLayout? = null
    private var listFavouriesMovie : MutableList<FavouritesTable> = mutableListOf()
    private var watchListMovie : MutableList<WatchListTable> = mutableListOf()
    private var mAuth: FirebaseAuth? = null
    private var adapterWatchListMovieView : WatchListMovieAdapter? = null
    private var adapterFavouriesMovieView : FavouriesMovieAdapter? = null
    private var homeFragmentcontext = context
    private var homeFragmentactivity = activity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val libraryFragment= inflater.inflate(R.layout.fragment_library, container, false)
        initView(libraryFragment)

        return libraryFragment
    }

    private fun initData() {
        val db = Room.databaseBuilder(homeFragmentcontext
            , MovieLocalDB::class.java
            ,"MyMovieDB")
            .fallbackToDestructiveMigration()
            .build()
        Thread{
                val favouritesMovieData = db.movieDAO().readAllFavouriesMovie()
                listFavouriesMovie.clear()
                listFavouriesMovie.addAll(favouritesMovieData)
                adapterFavouriesMovieView = FavouriesMovieAdapter(homeFragmentactivity,
                    listFavouriesMovie,homeFragmentcontext)
            homeFragmentactivity.runOnUiThread(Runnable {
                rvFavouritesMovie!!.adapter = adapterFavouriesMovieView
                rvFavouritesMovie!!.adapter!!.notifyDataSetChanged()
            })
        }.start()

        Thread{
            val watchMovieData = db.movieDAO().readAllWatchListTable()
            watchListMovie.clear()
            watchListMovie.addAll(watchMovieData)
            adapterWatchListMovieView = WatchListMovieAdapter(homeFragmentactivity,
                watchListMovie,homeFragmentcontext)
            homeFragmentactivity.runOnUiThread(Runnable {
                rvWatchListMovie!!.adapter = adapterWatchListMovieView
                rvWatchListMovie!!.adapter!!.notifyDataSetChanged()
            })

        }.start()


    }

    private fun initView(libraryFragment: View) {
        watchListGridLayoutManager = GridLayoutManager(context,3)
        watchListGridLayoutManager!!.orientation = GridLayoutManager.VERTICAL
        favouritesListGridLayoutManager = GridLayoutManager(context,3)
        favouritesListGridLayoutManager!!.orientation = GridLayoutManager.VERTICAL

        rvWatchListMovie = libraryFragment.findViewById(R.id.rvWatchListMovie)
        rvWatchListMovie!!.layoutManager = watchListGridLayoutManager
        rvWatchListMovie!!.isNestedScrollingEnabled = true
        rvWatchListMovie!!.setHasFixedSize(true)

        rvFavouritesMovie = libraryFragment.findViewById(R.id.rvFavouritesMovie)
        rvFavouritesMovie!!.layoutManager = favouritesListGridLayoutManager
        rvFavouritesMovie!!.isNestedScrollingEnabled = true
        rvFavouritesMovie!!.setHasFixedSize(true)


        llLibrary1 = libraryFragment.findViewById(R.id.llLibrary1)
        llLibrary2 = libraryFragment.findViewById(R.id.llLibrary2)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth!!.currentUser
        if(currentUser != null ){
            llLibrary1!!.visibility = View.GONE
            llLibrary2!!.visibility = View.VISIBLE
        }
        else{
            llLibrary1!!.visibility = View.VISIBLE
            llLibrary2!!.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        initData()
    }
}
