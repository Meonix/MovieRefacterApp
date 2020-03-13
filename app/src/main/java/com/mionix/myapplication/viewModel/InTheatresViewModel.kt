package com.mionix.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mionix.myapplication.base.onFailure
import com.mionix.myapplication.base.onLoading
import com.mionix.myapplication.base.onSuccess
import com.mionix.myapplication.model.CastAndCrew
import com.mionix.myapplication.model.ListMovie
import com.mionix.myapplication.model.Movie
import com.mionix.myapplication.repo.*
import com.mionix.myapplication.base.BaseViewModel

class InTheatresViewModel (private val mListVietNamMovieRepo: ListVietNamMovieRepo): BaseViewModel(){
    private val _getVietNamMovie = MutableLiveData<ListMovie>()
    val getVietNamMovie: LiveData<ListMovie> get() = _getVietNamMovie
    fun getVietNamMovie() = executeUseCase {
        mListVietNamMovieRepo.getVietNamMovie()
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                _getVietNamMovie.value = it
            }

            .onFailure {
                println("err $it")
            }
    }
}