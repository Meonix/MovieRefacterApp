package com.mionix.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mionix.myapplication.base.onFailure
import com.mionix.myapplication.base.onLoading
import com.mionix.myapplication.base.onSuccess
import com.mionix.myapplication.model.ListMovie
import com.mionix.myapplication.repo.ListPopularMovieRepo
import com.nice.app_ex.base.BaseViewModel

class MainViewModel (private val mListPopularMovie: ListPopularMovieRepo): BaseViewModel(){

    private val _getListPopularMovie = MutableLiveData<ListMovie>()
    val getListPopularMovie: LiveData<ListMovie> get() = _getListPopularMovie
    fun getListPopularMovie(page: Int) = executeUseCase {
        mListPopularMovie.getPoppularMovie(page)
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                _getListPopularMovie.value = it
            }

            .onFailure {
                println("err $it")
            }
    }

}