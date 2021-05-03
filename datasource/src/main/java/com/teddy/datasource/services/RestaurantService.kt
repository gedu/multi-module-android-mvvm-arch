package com.teddy.datasource.services

import com.teddy.common.model.network.DataResponse
import com.teddy.datasource.model.remote.RestaurantSerial
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantService {

    companion object {
        const val RESTAURANT_GET_INFO = "restaurant_get_info"
    }

    @GET("/api")
    suspend fun fetchRestaurantById(
        @Query("method") method: String,
        @Query("id_restaurant") restaurantId: String
    ): Response<DataResponse<RestaurantSerial>>
}