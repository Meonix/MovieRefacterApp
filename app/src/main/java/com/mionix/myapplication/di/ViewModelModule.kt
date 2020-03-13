package com.mionix.myapplication.di

import com.mionix.myapplication.viewModel.HomeFragmentViewModel
import com.mionix.myapplication.viewModel.InTheatresViewModel
import com.mionix.myapplication.viewModel.MovileDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { InTheatresViewModel(get()) }
    viewModel { HomeFragmentViewModel(get(),get()) }
    viewModel { MovileDetailViewModel(get(),get()) }

}