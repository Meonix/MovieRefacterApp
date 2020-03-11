package com.mionix.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.mionix.myapplication.R
import com.mionix.myapplication.api.POSTER_BASE_URL
import com.mionix.myapplication.localDataBase.MovieLocalDatabase
import com.mionix.myapplication.localDataBase.WatchListMovieDatabase
import com.mionix.myapplication.model.Cast
import com.mionix.myapplication.model.Movie
import com.mionix.myapplication.view.adapter.CastAdapter
import com.mionix.myapplication.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetail : AppCompatActivity() {
    private lateinit var ivBackDropMovieDetail : ImageView
    private lateinit var ivMovieDetail : ImageView
    private lateinit var tvTitleDetailMovie : TextView
    private lateinit var tvReleaseDate : TextView
    private lateinit var tvGenre : TextView
    private lateinit var tvVote : TextView
    private lateinit var btAddToWatchList :Button
    private lateinit var btAddToFavourites :Button
    private lateinit var poster_path:String
    private lateinit var tvDescriptionText : TextView
    private lateinit var rvCast : RecyclerView
    private lateinit var adapterCastDetail : CastAdapter
    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var watchMovie : Movie
    private lateinit var favouritesMovie : Movie
    private var mAuth: FirebaseAuth? = null
    private var listCast :MutableList<Cast> = mutableListOf()
    private val myViewModel : MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        initViewMovieDetail()
        val movie_id:String = intent.extras!!["movie_id"].toString()
        poster_path= intent.extras!!["poster_path"].toString()
        setupViewModel(movie_id,poster_path)
        btAddToFavourites.setOnClickListener{
            val currentUser = mAuth!!.currentUser
            if(currentUser != null){
                myViewModel.getMovie(movie_id.toInt())
                myViewModel.getDataMovieDetail.observe(this, Observer {
                    favouritesMovie = it
                })
                val db = MovieLocalDatabase(this@MovieDetail)
                val timestampLong = System.currentTimeMillis()/60000
                val timestamp = timestampLong.toString()
                db.insertFavouriteListData(favouritesMovie,timestamp)
            }
            else{
                Toast.makeText(this,"You have to login",Toast.LENGTH_SHORT).show()
            }
        }
        btAddToWatchList.setOnClickListener {
            val currentUser = mAuth!!.currentUser
            if(currentUser != null){
                myViewModel.getMovie(movie_id.toInt())
                myViewModel.getDataMovieDetail.observe(this, Observer {
                    watchMovie = it
                })
                val db = MovieLocalDatabase(this@MovieDetail)
                val timestampLong = System.currentTimeMillis()/60000
                val timestamp = timestampLong.toString()
                db.insertWatchListData(watchMovie,timestamp)
            }
            else{
                Toast.makeText(this,"You have to login",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViewModel(movie_id: String,poster_path:String) {

        Glide.with(this) //1
            .load(poster_path)
            .into(ivMovieDetail)
        myViewModel.getMovie(movie_id.toInt())
        myViewModel.getDataMovieDetail.observe(this, Observer {


            tvDescriptionText.text = it.overview
            tvTitleDetailMovie.text = it.title
            tvGenre.text = it.genres[0].name
            tvVote.text = it.vote_average.toString()
            tvReleaseDate.text = it.release_date
            Glide.with(this) //1
                .load(POSTER_BASE_URL + it.backdrop_path)
                .into(ivBackDropMovieDetail)
        })

        myViewModel.getDataCastAndCrew(movie_id.toInt())
        myViewModel.getDataCastAndCrew.observe(this, Observer {
            listCast.addAll(it.cast)
            rvCast.adapter!!.notifyDataSetChanged()
        })
        adapterCastDetail = CastAdapter(this,listCast,this)
        rvCast.adapter = adapterCastDetail
    }

    private fun initViewMovieDetail() {
        mAuth = FirebaseAuth.getInstance()
        tvDescriptionText = findViewById(R.id.tvDescriptionText)
        ivBackDropMovieDetail = findViewById(R.id.ivBackDropMovieDetail)
        ivMovieDetail = findViewById(R.id.ivMovieDetail)
        tvTitleDetailMovie = findViewById(R.id.tvTitleDetailMovie)
        tvReleaseDate = findViewById(R.id.tvReleaseDate)
        tvGenre = findViewById(R.id.tvGenre)
        tvVote = findViewById(R.id.tvVote)
        tvVote.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star,0,0,0)
        btAddToWatchList = findViewById(R.id.btAddToWatchList)
        btAddToFavourites = findViewById(R.id.btAddToFavourites)
        rvCast =findViewById(R.id.rvCastMovieDetail)
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rvCast.layoutManager = linearLayoutManager
        rvCast.isNestedScrollingEnabled = true
        rvCast.setHasFixedSize(true)
    }
}
