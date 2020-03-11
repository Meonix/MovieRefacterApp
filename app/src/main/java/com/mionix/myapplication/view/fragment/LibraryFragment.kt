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
import com.mionix.myapplication.localDataBase.FavouritesMovieDatabase
import com.mionix.myapplication.model.LocalSavedMovie
import com.mionix.myapplication.view.adapter.WatchListMovieAdapter

/**
 * A simple [Fragment] subclass.
 */
class LibraryFragment(context : Context, activity: Activity) : Fragment() {
    private lateinit var watchListGridLayoutManager: GridLayoutManager
    private lateinit var favouritesListGridLayoutManager: GridLayoutManager
    private lateinit var rvWatchListMovie :RecyclerView
    private lateinit var rvFavouritesMovie :RecyclerView
    private lateinit var llLibrary1 : LinearLayout
    private lateinit var llLibrary2 : LinearLayout
    private var listFavouriesMovie : MutableList<LocalSavedMovie> = mutableListOf()
    private var mAuth: FirebaseAuth? = null
    private lateinit var adapterTopRateMovieView : WatchListMovieAdapter
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
        val db = FavouritesMovieDatabase(homeFragmentcontext)
        val data = db.readData()
        listFavouriesMovie.clear()
        listFavouriesMovie.addAll(data)
        adapterTopRateMovieView = WatchListMovieAdapter(homeFragmentactivity,
            listFavouriesMovie,homeFragmentcontext)
        rvFavouritesMovie.adapter = adapterTopRateMovieView
        rvFavouritesMovie.adapter!!.notifyDataSetChanged()

    }

    private fun initView(libraryFragment: View) {
        watchListGridLayoutManager = GridLayoutManager(context,3)
        watchListGridLayoutManager.orientation = GridLayoutManager.VERTICAL
        favouritesListGridLayoutManager = GridLayoutManager(context,3)
        favouritesListGridLayoutManager.orientation = GridLayoutManager.VERTICAL

        rvWatchListMovie = libraryFragment.findViewById(R.id.rvWatchListMovie)
        rvWatchListMovie.layoutManager = watchListGridLayoutManager
        rvWatchListMovie.isNestedScrollingEnabled = true
        rvWatchListMovie.setHasFixedSize(true)

        rvFavouritesMovie = libraryFragment.findViewById(R.id.rvFavouritesMovie)
        rvFavouritesMovie.layoutManager = favouritesListGridLayoutManager
        rvFavouritesMovie.isNestedScrollingEnabled = true
        rvFavouritesMovie.setHasFixedSize(true)


        llLibrary1 = libraryFragment.findViewById(R.id.llLibrary1)
        llLibrary2 = libraryFragment.findViewById(R.id.llLibrary2)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth!!.currentUser
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

}
