package com.teddy.business.restaurantdetails

import com.teddy.common.model.Restaurant
import com.teddy.datasource.local.RestaurantLocalSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestaurantDetailsRepository(private val localSource: RestaurantLocalSource) {

    fun getRestaurantInfoBy(restaurantId: Long): Flow<Restaurant?> = flow {
        emit(localSource.getItemBy(restaurantId))
    }
}