package com.mionix.myapplication.di


import com.mionix.myapplication.repo.ListPopularMovieRepo
import com.mionix.myapplication.repo.ListTopRateMovieRepo
import org.koin.dsl.module

val repositoryModule = module {
      single { ListPopularMovieRepo(get()) }
      single { ListTopRateMovieRepo(get()) }
}