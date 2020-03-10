package com.mionix.myapplication.di


import com.mionix.myapplication.repo.CastAndCrewRepo
import com.mionix.myapplication.repo.ListPopularMovieRepo
import com.mionix.myapplication.repo.ListTopRateMovieRepo
import com.mionix.myapplication.repo.MovieRepo
import org.koin.dsl.module

val repositoryModule = module {
      single { ListPopularMovieRepo(get()) }
      single { ListTopRateMovieRepo(get()) }
      single { MovieRepo(get()) }
      single { CastAndCrewRepo(get()) }
}