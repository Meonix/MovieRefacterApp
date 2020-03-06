package com.mionix.myapplication.di


import com.mionix.myapplication.repo.ListPopularMovieRepo
import org.koin.dsl.module

val repositoryModule = module {
      single { ListPopularMovieRepo(get()) }
}