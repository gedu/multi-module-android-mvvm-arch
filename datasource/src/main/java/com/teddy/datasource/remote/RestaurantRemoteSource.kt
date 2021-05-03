package com.teddy.datasource.remote

import com.teddy.common.extensions.parse
import com.teddy.common.extensions.toHumanReadable
import com.teddy.common.model.Restaurant
import com.teddy.common.model.network.NetResult
import com.teddy.datasource.services.RestaurantService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

interface RestaurantRemoteCase {
    fun fetchRestaurantInfo(restaurantIds: List<String>): Flow<NetResult<Restaurant>>
    fun baseTestData(): List<String> = listOf("6861", "16409", "14163")
}

@FlowPreview
class RestaurantRemoteSource(private val service: RestaurantService) : RestaurantRemoteCase {
    override fun fetchRestaurantInfo(restaurantIds: List<String>): Flow<NetResult<Restaurant>> =
        restaurantIds.asFlow().flatMapMerge { id ->
            flow {
                emit(service.fetchRestaurantById(RestaurantService.RESTAURANT_GET_INFO, id))
            }.catch { emit(it.toHumanReadable()) }
        }.map { response -> response.parse { data -> data.parse() } }
            .flowOn(Dispatchers.IO)
}