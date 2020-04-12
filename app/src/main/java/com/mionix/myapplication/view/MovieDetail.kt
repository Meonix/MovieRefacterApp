package com.mionix.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.mionix.myapplication.R
import com.mionix.myapplication.api.POSTER_BASE_URL
import com.mionix.myapplication.localDataBase.FavouritesTable
import com.mionix.myapplication.localDataBase.MovieLocalDB
import com.mionix.myapplication.localDataBase.WatchListTable
import com.mionix.myapplication.model.Cast
import com.mionix.myapplication.model.Movie
import com.mionix.myapplication.view.adapter.CastAdapter
import com.mionix.myapplication.viewModel.MovileDetailViewModel
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetail : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private val movileDetailViewModel : MovileDetailViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        initViewMovieDetail()
        val movie_id:String = intent.extras?.get("movie_id").toString()
        val poster_path:String = intent.extras?.get("poster_path").toString()
        setupViewModel(movie_id,poster_path)
        btAddToFavourites.setOnClickListener{
            val currentUser = mAuth.currentUser
            lateinit var favouritesMovie : Movie
            if(currentUser != null){
                movileDetailViewModel.getMovie(movie_id.toInt())
                movileDetailViewModel.getDataMovieDetail.observe(this, Observer {
                    favouritesMovie = it
                })
                val db = Room.databaseBuilder(applicationContext
                    ,MovieLocalDB::class.java
                    ,"MyMovieDB")
                    .fallbackToDestructiveMigration()
                    .build()

                val timestampLong = System.currentTimeMillis()/60000
                val timestamp = timestampLong.toString()
                Thread{

                    if(db.movieDAO().readFavouriesMovie(favouritesMovie.id).isEmpty()){
                        val favouritesTable = FavouritesTable(favouritesMovie.id,
                            favouritesMovie.title,
                            favouritesMovie.poster_path,
                            favouritesMovie.overview,
                            timestamp)
                        db.movieDAO().saveFavouriesMovie(favouritesTable)
                    }
                    else{
                        db.movieDAO().readFavouriesMovie(favouritesMovie.id).forEach {
                            Log.i("@Mionix","""" Id id: ${it.colOverview} """")
                    }                    }
                }.start()
            }
            else{
                Toast.makeText(this,"You have to login",Toast.LENGTH_SHORT).show()
            }
        }
        btAddToWatchList.setOnClickListener {
            val currentUser = mAuth.currentUser
            lateinit var watchMovie : Movie
            if(currentUser != null){
                movileDetailViewModel.getMovie(movie_id.toInt())
                movileDetailViewModel.getDataMovieDetail.observe(this, Observer {
                    watchMovie = it
                })
                val db = Room.databaseBuilder(applicationContext
                    ,MovieLocalDB::class.java
                    ,"MyMovieDB")
                    .fallbackToDestructiveMigration()
                    .build()
                val timestampLong = System.currentTimeMillis()/60000
                val timestamp = timestampLong.toString()
                Thread{
                    val watchListTable = WatchListTable(watchMovie.id,
                        watchMovie.title,
                        watchMovie.poster_path,
                        watchMovie.overview,
                        timestamp)
                    if(db.movieDAO().readWatchListTable(watchMovie.id).isEmpty()){
                        db.movieDAO().saveWatchListTable(watchListTable)
                    }
                    else{
                        db.movieDAO().readWatchListTable(watchMovie.id).forEach {
                            Log.i("@Mionix","""" Id id: ${it.colOverview} """")
                        }
                    }
                }.start()

            }
            else{
                Toast.makeText(this,"You have to login",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViewModel(movie_id: String,poster_path:String) {
        val listCast :MutableList<Cast> = mutableListOf()
        val adapterCastDetail  = CastAdapter(this,listCast,this)
        Glide.with(this) //1
            .load(poster_path)
            .into(ivMovieDetail)
        movileDetailViewModel.getMovie(movie_id.toInt())
        movileDetailViewModel.getDataMovieDetail.observe(this, Observer {


            tvDescriptionText.text = it.overview
            tvTitleDetailMovie.text = it.title
            tvGenre.text = it.genres[0].name
            tvVote.text = it.vote_average.toString()
            tvReleaseDate.text = it.release_date
            Glide.with(this) //1
                .load(POSTER_BASE_URL + it.backdrop_path)
                .into(ivBackDropMovieDetail)
        })

        movileDetailViewModel.getDataCastAndCrew(movie_id.toInt())
        movileDetailViewModel.getDataCastAndCrew.observe(this, Observer {
            listCast.addAll(it.cast)
            adapterCastDetail.update()
        })
        rvCastMovieDetail.adapter = adapterCastDetail
    }

    private fun initViewMovieDetail() {
        val linearLayoutManager  = LinearLayoutManager(this)
        mAuth = FirebaseAuth.getInstance()
        tvVote.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star,0,0,0)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rvCastMovieDetail.layoutManager = linearLayoutManager
        rvCastMovieDetail.isNestedScrollingEnabled = true
        rvCastMovieDetail.setHasFixedSize(true)
    }
}
