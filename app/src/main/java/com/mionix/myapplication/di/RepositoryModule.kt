package com.mionix.myapplication.di


import com.mionix.myapplication.repo.MovieRepo
import org.koin.dsl.module

val repositoryModule = module {
      single { MovieRepo(get()) }
}