package com.mionix.myapplication.di


import com.mionix.myapplication.repo.*
import org.koin.dsl.module

val repositoryModule = module {
      single { ListPopularMovieRepo(get()) }
      single { ListTopRateMovieRepo(get()) }
      single { MovieRepo(get()) }
      single { CastAndCrewRepo(get()) }
      single { ListVietNamMovieRepo(get()) }
}