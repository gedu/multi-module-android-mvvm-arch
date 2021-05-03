package com.teddy.datasource

import com.teddy.common.model.network.DataResponse
import com.teddy.common.model.network.NetResult
import com.teddy.datasource.local.RestaurantLocalSource
import com.teddy.datasource.models.mockedRestaurantSerials
import com.teddy.datasource.remote.RestaurantRemoteSource
import com.teddy.datasource.services.RestaurantService
import com.teddy.datasource.utils.BaseTest
import com.teddy.datasource.utils.randomId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import retrofit2.Response

@FlowPreview
@ExperimentalCoroutinesApi
class RestaurantLocalSourceTest : BaseTest() {

    @Test
    fun `Should parse from RestaurantSerial to Restaurant from Success response`() = runBlocking {
        val pathRemoteSource = RestaurantLocalSource()
        val actualId = randomId
        val idLong = actualId.toLong()
        val actualRestaurant = mockedRestaurantSerials.singleRecord(actualId)

        pathRemoteSource.addItem(idLong, actualRestaurant.parse())

        val savedRestaurant = pathRemoteSource.getItemBy(idLong)

        Assert.assertEquals(actualId.toLong(), savedRestaurant?.idRestaurant)
    }
}