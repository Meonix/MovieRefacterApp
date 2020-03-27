package com.mionix.myapplication.view.fragment


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_recycleview_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), OnItemClickListener {
    private lateinit var adapterPopularMovieView : PopularMovieAdapter
    private lateinit var adapterTopRateMovieView : TopRateMovieAdapter
    private var pageTopRate = 1
    private var pagePopular = 1
    private var popularIsLoading = true
    private var topRateIsLoading = true

    private val listPopularMovie :MutableList<Result> = mutableListOf()
    private val listTopRateMovie :MutableList<Result> = mutableListOf()

    private val homeFragmentViewModel : HomeFragmentViewModel by viewModel()
    private lateinit var popularMovieGridLayoutManager: GridLayoutManager
    private lateinit var topRateMovieGridLayoutManager: GridLayoutManager
    private var homeFragmentcontext = context
    private var homeFragmentactivity = activity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeFragment = inflater.inflate(R.layout.fragment_home, container, false)
        return homeFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listPopularMovie.clear()
        listTopRateMovie.clear()
        initView()
        setupViewModel()
    }
    private fun initView(){
        popularMovieGridLayoutManager = GridLayoutManager(context,3)
        popularMovieGridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        topRateMovieGridLayoutManager = GridLayoutManager(context,3)
        topRateMovieGridLayoutManager.orientation = GridLayoutManager.HORIZONTAL

        rvPopularMovie.layoutManager = popularMovieGridLayoutManager
        rvPopularMovie.isNestedScrollingEnabled = true
        rvPopularMovie.setHasFixedSize(true)

        rvTopRateMovie.layoutManager = topRateMovieGridLayoutManager
        rvTopRateMovie.isNestedScrollingEnabled = true
        rvTopRateMovie.setHasFixedSize(true)
    }
    private fun setupViewModel() {

        // get data and init PopularMovie Recycle View
        homeFragmentViewModel.getListPopularMovie(pagePopular)
        homeFragmentViewModel.getListPopularMovie.observe(this, Observer {

            listPopularMovie.addAll(it.results)
            rvPopularMovie.adapter?.notifyDataSetChanged()
        })
        adapterPopularMovieView =
            PopularMovieAdapter(
                activity
                ,listPopularMovie
                ,context
                ,this)
        rvPopularMovie.adapter = adapterPopularMovieView

        rvPopularMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dx > 0) {
                val visibleItemCount = popularMovieGridLayoutManager.childCount
                val pastVisibleItem = popularMovieGridLayoutManager.findFirstCompletelyVisibleItemPosition()
                val total = adapterTopRateMovieView.itemCount
                if (popularIsLoading) {
                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        pagePopular += 1

                        getPopularPage()
                        popularIsLoading = false
                    }
                }
              }
                super.onScrolled(recyclerView, dx, dy)
            }

        })

        // get data and init Top Rated Movie Recycle View
        homeFragmentViewModel.getTopRateMovie(pageTopRate)
        homeFragmentViewModel.getTopRateMovie.observe(this, Observer {

            listTopRateMovie.addAll(it.results)
            rvTopRateMovie.adapter?.notifyDataSetChanged()
        })
        adapterTopRateMovieView =
            TopRateMovieAdapter(
                activity
                ,listTopRateMovie
                ,context
                ,this)
        rvTopRateMovie.adapter = adapterTopRateMovieView

        rvTopRateMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (dx > 0) {
                val visibleItemCount = topRateMovieGridLayoutManager.childCount
                val pastVisibleItem = topRateMovieGridLayoutManager.findFirstCompletelyVisibleItemPosition()
                val total = adapterTopRateMovieView.itemCount
                if (topRateIsLoading) {
                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        pageTopRate += 1

                        getTopRatePage()
                        topRateIsLoading = false
                    }
                }
//              }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun onItemClicked(listPopularMovie: Result) {
        val moviePosterURL = POSTER_BASE_URL + listPopularMovie.posterPath
        val intent = Intent(context, MovieDetail::class.java)
        intent.putExtra("movie_id",listPopularMovie.id)
        intent.putExtra("poster_path",moviePosterURL)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
    private fun getPopularPage(){
        popularIsLoading = true
        popularProgressBar.visibility = View.VISIBLE
        Handler().postDelayed({
            homeFragmentViewModel.getListPopularMovie(pagePopular)
            popularProgressBar.visibility = View.GONE

            popularIsLoading = true
        },1200)
    }
    private fun getTopRatePage(){
        topRateIsLoading = true
        topRateProgressBar.visibility = View.VISIBLE
        Handler().postDelayed({
            homeFragmentViewModel.getTopRateMovie(pageTopRate)
            topRateProgressBar.visibility = View.GONE

            topRateIsLoading = true
        },1200)
    }
}
