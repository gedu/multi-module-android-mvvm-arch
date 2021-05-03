package com.teddy.datasource.remote

import com.teddy.common.model.network.DataResponse
import com.teddy.common.model.network.NetResult
import com.teddy.datasource.models.mockedRestaurantSerials
import com.teddy.datasource.services.RestaurantService
import com.teddy.datasource.utils.BaseTest
import com.teddy.datasource.utils.randomId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import retrofit2.Response

@FlowPreview
@ExperimentalCoroutinesApi
class RestaurantRemoteSourceTest : BaseTest() {

    @Test
    fun `Should parse from RestaurantSerial to Restaurant from Success response`() = runBlocking {
        val restaurantService = Mockito.mock(RestaurantService::class.java)
        val pathRemoteSource = RestaurantRemoteSource(restaurantService)
        val actualId = randomId
        val idString = actualId.toString()
        val ids = listOf(idString)

        whenever(restaurantService.fetchRestaurantById(RestaurantService.RESTAURANT_GET_INFO, idString)).thenReturn(
            Response.success(
                DataResponse(
                    mockedRestaurantSerials.singleRecord(actualId)
                )
            )
        )

        pathRemoteSource.fetchRestaurantInfo(ids).collect {
            Assert.assertTrue(it is NetResult.Success)
            Assert.assertEquals(actualId.toLong(), (it as NetResult.Success).data.idRestaurant)
        }
    }

    @Test
    fun `RestaurantSerial Success empty content should be an error response`() = runBlocking {
        val restaurantService = Mockito.mock(RestaurantService::class.java)
        val pathRemoteSource = RestaurantRemoteSource(restaurantService)
        val actualId = randomId
        val idString = actualId.toString()
        val ids = listOf(idString)

        whenever(restaurantService.fetchRestaurantById(RestaurantService.RESTAURANT_GET_INFO, idString)).thenReturn(
            Response.success(null)
        )

        pathRemoteSource.fetchRestaurantInfo(ids).collect {
            Assert.assertTrue(it is NetResult.Error)
        }
    }

    @Test
    fun `RestaurantSerial 500 should be an error response`() = runBlocking {
        val restaurantService = Mockito.mock(RestaurantService::class.java)
        val pathRemoteSource = RestaurantRemoteSource(restaurantService)
        val actualId = randomId
        val idString = actualId.toString()
        val ids = listOf(idString)

        whenever(restaurantService.fetchRestaurantById(RestaurantService.RESTAURANT_GET_INFO, idString)).thenReturn(
            Response.error(
                500,
                "Test Error Message".toResponseBody()
            )
        )

        pathRemoteSource.fetchRestaurantInfo(ids).collect {
            Assert.assertTrue(it is NetResult.Error)
        }
    }
}