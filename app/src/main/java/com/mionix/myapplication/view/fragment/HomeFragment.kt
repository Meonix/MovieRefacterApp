package com.mionix.myapplication.view.fragment


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mionix.myapplication.R
import com.mionix.myapplication.api.POSTER_BASE_URL
import com.mionix.myapplication.model.Result
import com.mionix.myapplication.view.adapter.OnItemClickListener
import com.mionix.myapplication.view.adapter.PopularMovieAdapter
import com.mionix.myapplication.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment(context :Context,activity: Activity) : Fragment(), OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterPopularMovieView : PopularMovieAdapter
    private val listPopularMovie :MutableList<Result> = mutableListOf()
    private val myViewModel : MainViewModel by viewModel()
    private lateinit var gridLayoutManager: GridLayoutManager
    private var homeFragmentcontext = context
    private var homeFragmentactivity = activity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeFragment = inflater.inflate(R.layout.fragment_home, container, false)
        initView(homeFragment)
        setupViewModel(homeFragmentcontext,homeFragmentactivity)
        return homeFragment
    }
    private fun initView(homeFragment: View){
        gridLayoutManager = GridLayoutManager(context,3)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        recyclerView = homeFragment.findViewById(R.id.rvPopularMovie)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.isNestedScrollingEnabled = true
        recyclerView.setHasFixedSize(true)
    }
    private fun setupViewModel(context :Context,activity: Activity) {
        myViewModel.getListPopularMovie(1)
        myViewModel.getListPopularMovie.observe(this, Observer {
            listPopularMovie.clear()
            listPopularMovie.addAll(it.results)
            recyclerView.adapter!!.notifyDataSetChanged()
        })

        adapterPopularMovieView =
            PopularMovieAdapter(activity
                ,listPopularMovie
                ,context
                ,this)

        recyclerView.adapter = adapterPopularMovieView
    }

    override fun onItemClicked(listPopularMovie: Result) {
        val moviePosterURL = POSTER_BASE_URL + listPopularMovie.posterPath

//        val intent = Intent(this@MainActivity, MovieDetail::class.java)
//        intent.putExtra("movie_id",listPopularMovie.id)
//        intent.putExtra("poster_path",moviePosterURL)
//        val options: ActivityOptionsCompat = ActivityOptionsCompat
//            .makeSceneTransitionAnimation(
//                this@MainActivity,ivPopularMovie
//                , ViewCompat.getTransitionName(ivPopularMovie).toString())
//        startActivity(intent,options.toBundle())
    }

}
