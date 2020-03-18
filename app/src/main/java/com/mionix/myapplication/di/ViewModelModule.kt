package com.mionix.myapplication.di

import com.mionix.myapplication.viewModel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { InTheatresViewModel(get()) }
    viewModel { HomeFragmentViewModel(get(),get()) }
    viewModel { MovileDetailViewModel(get(),get()) }
    viewModel { LoginViewModel() }
    viewModel { RegisterViewModel() }
}