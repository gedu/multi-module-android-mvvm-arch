package com.teddy.business.home

import com.teddy.business.models.mockedRestaurant
import com.teddy.business.utils.BaseTest
import com.teddy.business.utils.LiveDataTestUtil
import com.teddy.business.utils.randomId
import com.teddy.business.utils.runBlocking
import com.teddy.common.model.network.NetResult
import com.teddy.datasource.local.RestaurantLocalSource
import com.teddy.datasource.remote.RestaurantRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.*

@FlowPreview
@ExperimentalCoroutinesApi
class HomeViewModelTest : BaseTest() {

    @Test
    fun `Fetching Restaurants Should notify start loading`() = coroutineRule.runBlocking {
        val repository = Mockito.mock(HomeRepository::class.java)

        whenever(repository.fetchRestaurantInfo()).thenReturn(flowOf(NetResult.StartLoading))

        val viewModel = HomeViewModel(repository)

        Assert.assertTrue(
            LiveDataTestUtil.getValue(viewModel.isLoadingRestaurant)!!
        )
    }

    @Test
    fun `Fetching Restaurants Should notify to hide a loading`() = coroutineRule.runBlocking {
        val repository = Mockito.mock(HomeRepository::class.java)

        whenever(repository.fetchRestaurantInfo()).thenReturn(flowOf(NetResult.StopLoading))

        val viewModel = HomeViewModel(repository)

        Assert.assertFalse(
            LiveDataTestUtil.getValue(viewModel.isLoadingRestaurant)!!
        )
    }

    @Test
    fun `Success Restaurants fetching Should notify a success result with data`() = coroutineRule.runBlocking {
        val remoteSource = Mockito.mock(RestaurantRemoteSource::class.java)
        val localSource = Mockito.mock(RestaurantLocalSource::class.java)
        val repository = HomeRepository(remoteSource, localSource)
        val actualId = randomId
        val mockedRes = mockedRestaurant.singleRecord(actualId)
        val mockedBaseIds = listOf(actualId.toString())

        whenever(remoteSource.baseTestData()).thenReturn(mockedBaseIds)
        whenever(remoteSource.fetchRestaurantInfo(mockedBaseIds)).thenReturn(flowOf(NetResult.Success(mockedRes)))

        var restaurants = listOf<RestaurantView>()

        val viewModel = HomeViewModel(repository)
        viewModel.restaurants.observeForever {
            restaurants = it
        }

        verify(localSource, times(1)).addItem(actualId.toLong(), mockedRes)

        Assert.assertEquals(actualId.toLong(), restaurants[0].idRestaurant)
    }

}