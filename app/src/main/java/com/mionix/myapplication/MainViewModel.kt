package com.mionix.myapplication

import com.mionix.myapplication.base.onFailure
import com.mionix.myapplication.base.onLoading
import com.mionix.myapplication.base.onSuccess
import com.mionix.myapplication.repo.MovieRepo
import com.nice.app_ex.base.BaseViewModel

class MainViewModel (private val mMovieRepo: MovieRepo): BaseViewModel(){

     fun getMovie() = executeUseCase {

         mMovieRepo.getMovie()
            .onLoading {
                println("Loading $it")
            }

            .onSuccess {
                println("success $it")
            }

            .onFailure {
                println("err $it")
            }
    }

}