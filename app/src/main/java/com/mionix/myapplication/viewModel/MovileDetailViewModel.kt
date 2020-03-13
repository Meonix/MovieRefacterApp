package com.mionix.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mionix.myapplication.base.BaseViewModel
import com.mionix.myapplication.base.onFailure
import com.mionix.myapplication.base.onLoading
import com.mionix.myapplication.base.onSuccess
import com.mionix.myapplication.model.CastAndCrew
import com.mionix.myapplication.model.Movie
import com.mionix.myapplication.repo.CastAndCrewRepo
import com.mionix.myapplication.repo.MovieRepo

class MovileDetailViewModel (private val mMovieRepo: MovieRepo,
                             private val mCastAndCrew: CastAndCrewRepo
): BaseViewModel(){
    private val _getDataMovieDetail = MutableLiveData<Movie>()
    val getDataMovieDetail: LiveData<Movie> get() = _getDataMovieDetail
    fun getMovie(movie_id: Int) = executeUseCase {
        mMovieRepo.getMovie(movie_id)
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                _getDataMovieDetail.value = it
            }

            .onFailure {
                println("err $it")
            }
    }

    private val _getDataCastAndCrew = MutableLiveData<CastAndCrew>()
    val getDataCastAndCrew:LiveData<CastAndCrew> get() = _getDataCastAndCrew
    fun getDataCastAndCrew(movie_id: Int) = executeUseCase {
        mCastAndCrew.getCastAndCrew(movie_id)
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                _getDataCastAndCrew.value = it
            }

            .onFailure {
                println("err $it")
            }
    }
}