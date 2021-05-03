package com.teddy.business.di

import com.teddy.business.home.HomeRepository
import com.teddy.business.home.HomeViewModel
import com.teddy.business.restaurantdetails.RestaurantDetailsRepository
import com.teddy.business.restaurantdetails.RestaurantDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
val businessModule = module {

    single { HomeRepository(get(), get()) }

    single { RestaurantDetailsRepository(get()) }

    viewModel { HomeViewModel(get()) }

    viewModel { RestaurantDetailsViewModel(get()) }
}