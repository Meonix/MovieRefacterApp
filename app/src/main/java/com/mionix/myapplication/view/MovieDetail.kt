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
import com.mionix.myapplication.localDataBase.MovieLocalDatabase
import com.mionix.myapplication.localDataBase.WatchListTable
import com.mionix.myapplication.model.Cast
import com.mionix.myapplication.model.Movie
import com.mionix.myapplication.view.adapter.CastAdapter
import com.mionix.myapplication.viewModel.MovileDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetail : AppCompatActivity() {
    private  var ivBackDropMovieDetail : ImageView? = null
    private var ivMovieDetail : ImageView? = null
    private var tvTitleDetailMovie : TextView? = null
    private var tvReleaseDate : TextView? = null
    private var tvGenre : TextView? = null
    private var tvVote : TextView? = null
    private var btAddToWatchList :Button? = null
    private var btAddToFavourites :Button? = null
    private var poster_path:String? = null
    private var tvDescriptionText : TextView? = null
    private var rvCast : RecyclerView? = null
    private var adapterCastDetail : CastAdapter? = null
    private var linearLayoutManager : LinearLayoutManager? = null
    private var watchMovie : Movie? = null
    private var favouritesMovie : Movie? = null
    private var mAuth: FirebaseAuth? = null
    private var listCast :MutableList<Cast> = mutableListOf()
    private val movileDetailViewModel : MovileDetailViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        initViewMovieDetail()
        val movie_id:String = intent.extras!!["movie_id"].toString()
        poster_path= intent.extras!!["poster_path"].toString()
        setupViewModel(movie_id,poster_path!!)
        btAddToFavourites!!.setOnClickListener{
            val currentUser = mAuth!!.currentUser
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

                    if(db.movieDAO().readFavouriesMovie(favouritesMovie!!.id).isEmpty()){
                        val favouritesTable = FavouritesTable(favouritesMovie!!.id,
                            favouritesMovie!!.title,
                            favouritesMovie!!.poster_path,
                            favouritesMovie!!.overview,
                            timestamp)
                        db.movieDAO().saveFavouriesMovie(favouritesTable)
                    }
                    else{
                        db.movieDAO().readFavouriesMovie(favouritesMovie!!.id).forEach {
                            Log.i("@Mionix","""" Id id: ${it.colOverview} """")
                    }                    }
                }.start()
            }
            else{
                Toast.makeText(this,"You have to login",Toast.LENGTH_SHORT).show()
            }
        }
        btAddToWatchList!!.setOnClickListener {
            val currentUser = mAuth!!.currentUser
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
                    val watchListTable = WatchListTable(watchMovie!!.id,
                        watchMovie!!.title,
                        watchMovie!!.poster_path,
                        watchMovie!!.overview,
                        timestamp)
                    if(db.movieDAO().readWatchListTable(watchMovie!!.id).isEmpty()){
                        db.movieDAO().saveWatchListTable(watchListTable)
                    }
                    else{
                        db.movieDAO().readWatchListTable(watchMovie!!.id).forEach {
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

        Glide.with(this) //1
            .load(poster_path)
            .into(ivMovieDetail!!)
        movileDetailViewModel.getMovie(movie_id.toInt())
        movileDetailViewModel.getDataMovieDetail.observe(this, Observer {


            tvDescriptionText!!.text = it.overview
            tvTitleDetailMovie!!.text = it.title
            tvGenre!!.text = it.genres[0].name
            tvVote!!.text = it.vote_average.toString()
            tvReleaseDate!!.text = it.release_date
            Glide.with(this) //1
                .load(POSTER_BASE_URL + it.backdrop_path)
                .into(ivBackDropMovieDetail!!)
        })

        movileDetailViewModel.getDataCastAndCrew(movie_id.toInt())
        movileDetailViewModel.getDataCastAndCrew.observe(this, Observer {
            listCast.addAll(it.cast)
            rvCast!!.adapter!!.notifyDataSetChanged()
        })
        adapterCastDetail = CastAdapter(this,listCast,this)
        rvCast!!.adapter = adapterCastDetail
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
        tvVote!!.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star,0,0,0)
        btAddToWatchList = findViewById(R.id.btAddToWatchList)
        btAddToFavourites = findViewById(R.id.btAddToFavourites)
        rvCast =findViewById(R.id.rvCastMovieDetail)
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager!!.orientation = LinearLayoutManager.HORIZONTAL

        rvCast!!.layoutManager = linearLayoutManager
        rvCast!!.isNestedScrollingEnabled = true
        rvCast!!.setHasFixedSize(true)
    }
}
