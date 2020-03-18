package com.mionix.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mionix.myapplication.base.BaseViewModel
import com.mionix.myapplication.base.onFailure
import com.mionix.myapplication.base.onLoading
import com.mionix.myapplication.base.onSuccess
import com.mionix.myapplication.model.ListMovie
import com.mionix.myapplication.repo.ListPopularMovieRepo
import com.mionix.myapplication.repo.ListTopRateMovieRepo

class HomeFragmentViewModel(private val mListPopularMovie: ListPopularMovieRepo,
                            private val mListTopRateMovie: ListTopRateMovieRepo): BaseViewModel() {


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

    private val _getListTopRateMovie = MutableLiveData<ListMovie>()
    val getTopRateMovie: LiveData<ListMovie> get() = _getListTopRateMovie
    fun getTopRateMovie(page: Int) = executeUseCase {
        mListTopRateMovie.getTopRateMovie(page)
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                _getListTopRateMovie.value = it
            }

            .onFailure {
                println("err $it")
            }
    }
}