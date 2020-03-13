package com.mionix.myapplication.view.fragment


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mionix.myapplication.R
import com.mionix.myapplication.api.POSTER_BASE_URL
import com.mionix.myapplication.model.Result
import com.mionix.myapplication.view.MovieDetail
import com.mionix.myapplication.view.adapter.OnItemClickListener
import com.mionix.myapplication.view.adapter.PopularMovieAdapter
import com.mionix.myapplication.view.adapter.TopRateMovieAdapter
import com.mionix.myapplication.viewModel.HomeFragmentViewModel
import kotlinx.android.synthetic.main.item_recycleview_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment(context :Context,activity: Activity) : Fragment(), OnItemClickListener {
    private var popularMovierecyclerView: RecyclerView? = null
    private var topRateMovierecyclerView: RecyclerView? = null
    private var adapterPopularMovieView : PopularMovieAdapter? = null
    private var adapterTopRateMovieView : TopRateMovieAdapter? = null


    private val listPopularMovie :MutableList<Result> = mutableListOf()
    private val listTopRateMovie :MutableList<Result> = mutableListOf()

    private val homeFragmentViewModel : HomeFragmentViewModel by viewModel()
    private var popularMovieGridLayoutManager: GridLayoutManager? = null
    private var topRateMovieGridLayoutManager: GridLayoutManager? = null
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
        popularMovieGridLayoutManager = GridLayoutManager(context,3)
        popularMovieGridLayoutManager!!.orientation = GridLayoutManager.VERTICAL
        topRateMovieGridLayoutManager = GridLayoutManager(context,3)
        topRateMovieGridLayoutManager!!.orientation = GridLayoutManager.VERTICAL

        popularMovierecyclerView = homeFragment.findViewById(R.id.rvPopularMovie)
        popularMovierecyclerView!!.layoutManager = popularMovieGridLayoutManager
        popularMovierecyclerView!!.isNestedScrollingEnabled = true
        popularMovierecyclerView!!.setHasFixedSize(true)

        topRateMovierecyclerView = homeFragment.findViewById(R.id.rvTopRateMovie)
        topRateMovierecyclerView!!.layoutManager = topRateMovieGridLayoutManager
        topRateMovierecyclerView!!.isNestedScrollingEnabled = true
        topRateMovierecyclerView!!.setHasFixedSize(true)
    }
    private fun setupViewModel(context :Context,activity: Activity) {

        // get data and init PopularMovie Recycle View
        homeFragmentViewModel.getListPopularMovie(1)
        homeFragmentViewModel.getListPopularMovie.observe(this, Observer {
            listPopularMovie.clear()
            listPopularMovie.addAll(it.results)
            popularMovierecyclerView!!.adapter!!.notifyDataSetChanged()
        })
        adapterPopularMovieView =
            PopularMovieAdapter(activity
                ,listPopularMovie
                ,context
                ,this)
        popularMovierecyclerView!!.adapter = adapterPopularMovieView
        // get data and init Top Rated Movie Recycle View
        homeFragmentViewModel.getTopRateMovie(1)
        homeFragmentViewModel.getTopRateMovie.observe(this, Observer {
            listTopRateMovie.clear()
            listTopRateMovie.addAll(it.results)
            topRateMovierecyclerView!!.adapter!!.notifyDataSetChanged()
        })
        adapterTopRateMovieView =
            TopRateMovieAdapter(activity
                ,listTopRateMovie
                ,context
                ,this)
        topRateMovierecyclerView!!.adapter = adapterTopRateMovieView
    }

    override fun onItemClicked(listPopularMovie: Result) {
        val moviePosterURL = POSTER_BASE_URL + listPopularMovie.posterPath
        val intent = Intent(context, MovieDetail::class.java)
        intent.putExtra("movie_id",listPopularMovie.id)
        intent.putExtra("poster_path",moviePosterURL)
        val options: ActivityOptionsCompat = ActivityOptionsCompat
            .makeSceneTransitionAnimation(
                homeFragmentactivity,ivItemRecycleView
                , ViewCompat.getTransitionName(ivItemRecycleView).toString())
        startActivity(intent,options.toBundle())
    }

}
