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
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_in_theatres.*
import kotlinx.android.synthetic.main.item_recycleview_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class InTheatresFragment : Fragment(), OnItemClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inTheatresFragment = inflater.inflate(R.layout.fragment_in_theatres, container, false)

        return inTheatresFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupViewModel()
    }

    private fun setupViewModel() {

        val listVietNamMovie :MutableList<Result> = mutableListOf()
        val adapterVietNamMovieView  = VietNamMovieAdapter(activity
            ,listVietNamMovie
            ,context
            ,this)

        val inTheatresViewModel : InTheatresViewModel by viewModel()
        inTheatresViewModel.getVietNamMovie()
        inTheatresViewModel.getVietNamMovie.observe(viewLifecycleOwner,androidx.lifecycle.Observer {
            listVietNamMovie.clear()
            listVietNamMovie.addAll(it.results)
            adapterVietNamMovieView.update()
        })
        rvVietNamMovie.adapter = adapterVietNamMovieView
    }

    private fun initView() {
        val vietnamMovieGridLayoutManager = GridLayoutManager(context,3)
        vietnamMovieGridLayoutManager.orientation = GridLayoutManager.VERTICAL

        rvVietNamMovie.layoutManager = vietnamMovieGridLayoutManager
        rvVietNamMovie.isNestedScrollingEnabled = true
        rvVietNamMovie.setHasFixedSize(true)
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

}
