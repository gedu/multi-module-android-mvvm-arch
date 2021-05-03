package com.teddy.business.home

import com.teddy.common.extensions.loading
import com.teddy.common.model.network.NetResult
import com.teddy.datasource.local.RestaurantLocalSource
import com.teddy.datasource.remote.RestaurantRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
@FlowPreview
class HomeRepository(
    private val remoteSource: RestaurantRemoteSource,
    private val localSource: RestaurantLocalSource
) {

    fun fetchRestaurantInfo(): Flow<NetResult<Any>> =
        remoteSource.fetchRestaurantInfo(remoteSource.baseTestData()).loading().map { result ->
            if (result is NetResult.Success) {
                val restaurant = result.data
                localSource.addItem(restaurant.idRestaurant, restaurant)
                return@map NetResult.Success(
                    RestaurantView(
                        restaurant.idRestaurant,
                        restaurant.name,
                        restaurant.address,
                        restaurant.avgRate,
                        restaurant.mainPics.medium
                    )
                )
            }
            result
        }
}