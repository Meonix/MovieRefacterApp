package com.mionix.myapplication.view.fragment


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

import com.mionix.myapplication.R
import com.mionix.myapplication.localDataBase.MovieLocalDatabase
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
    private var listFavouriesMovie : MutableList<LocalSavedMovie> = mutableListOf()
    private var watchListMovie : MutableList<LocalSavedMovie> = mutableListOf()
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
        val db = MovieLocalDatabase(homeFragmentcontext)
        val favouritesMovieData = db.readFavouriteListData()

        listFavouriesMovie.clear()
        listFavouriesMovie.addAll(favouritesMovieData)
        adapterFavouriesMovieView = FavouriesMovieAdapter(homeFragmentactivity,
            listFavouriesMovie,homeFragmentcontext)
        rvFavouritesMovie!!.adapter = adapterFavouriesMovieView
        rvFavouritesMovie!!.adapter!!.notifyDataSetChanged()

        val watchMovieData = db.readWatchListData()
        watchListMovie.clear()
        watchListMovie.addAll(watchMovieData)
        adapterWatchListMovieView = WatchListMovieAdapter(homeFragmentactivity,
            watchListMovie,homeFragmentcontext)
        rvWatchListMovie!!.adapter = adapterWatchListMovieView
        rvWatchListMovie!!.adapter!!.notifyDataSetChanged()

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
