package com.mionix.myapplication.view.fragment


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mionix.myapplication.R
import com.mionix.myapplication.api.POSTER_BASE_URL
import com.mionix.myapplication.model.Result
import com.mionix.myapplication.view.MovieDetail
import com.mionix.myapplication.view.adapter.OnItemClickListener
import com.mionix.myapplication.view.adapter.PopularMovieAdapter
import com.mionix.myapplication.view.adapter.VietNamMovieAdapter
import com.mionix.myapplication.viewModel.InTheatresViewModel
import kotlinx.android.synthetic.main.item_recycleview_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class InTheatresFragment(context : Context, activity: Activity) : Fragment(), OnItemClickListener {
    private var vietnamMovierecyclerView: RecyclerView? = null
    private var adapterVietNamMovieView : VietNamMovieAdapter? = null
    private val listVietNamMovie :MutableList<Result> = mutableListOf()
    private val inTheatresViewModel : InTheatresViewModel by viewModel()
    private var vietnamMovieGridLayoutManager: GridLayoutManager? = null
    private var inTheatresFragmentcontext = context
    private var inTheatresFragmentactivity = activity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inTheatresFragment = inflater.inflate(R.layout.fragment_in_theatres, container, false)
        initView(inTheatresFragment)
        setupViewModel(inTheatresFragmentcontext,inTheatresFragmentactivity)
        return inTheatresFragment
    }

    private fun setupViewModel(homeFragmentcontext: Context, homeFragmentactivity: Activity) {
        inTheatresViewModel.getVietNamMovie()
        inTheatresViewModel.getVietNamMovie.observe(this,androidx.lifecycle.Observer {
            listVietNamMovie.clear()
            listVietNamMovie.addAll(it.results)
            vietnamMovierecyclerView!!.adapter!!.notifyDataSetChanged()
        })
        adapterVietNamMovieView =
            VietNamMovieAdapter(homeFragmentactivity
                ,listVietNamMovie
                ,homeFragmentcontext
                ,this)
        vietnamMovierecyclerView!!.adapter = adapterVietNamMovieView
    }

    private fun initView(inTheatresFragment: View) {
        vietnamMovieGridLayoutManager = GridLayoutManager(context,3)
        vietnamMovieGridLayoutManager!!.orientation = GridLayoutManager.VERTICAL

        vietnamMovierecyclerView = inTheatresFragment.findViewById(R.id.rvVietNamMovie)
        vietnamMovierecyclerView!!.layoutManager = vietnamMovieGridLayoutManager
        vietnamMovierecyclerView!!.isNestedScrollingEnabled = true
        vietnamMovierecyclerView!!.setHasFixedSize(true)
    }
    override fun onItemClicked(listPopularMovie: Result) {
        val moviePosterURL = POSTER_BASE_URL + listPopularMovie.posterPath
        val intent = Intent(context, MovieDetail::class.java)
        intent.putExtra("movie_id",listPopularMovie.id)
        intent.putExtra("poster_path",moviePosterURL)
        val options: ActivityOptionsCompat = ActivityOptionsCompat
            .makeSceneTransitionAnimation(
                inTheatresFragmentactivity,ivItemRecycleView
                , ViewCompat.getTransitionName(ivItemRecycleView).toString())
        startActivity(intent,options.toBundle())
    }


}
