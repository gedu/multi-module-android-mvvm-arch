package com.teddy.business.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teddy.common.model.network.NetResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    enum class WrongState {
        EMPTY, ERROR
    }

    private val _loadingError = MutableLiveData<WrongState?>(null)
    val loadingError: LiveData<WrongState?> get() = _loadingError

    private val _loadingRestaurants = MutableLiveData<Boolean>(true)
    val isLoadingRestaurant: LiveData<Boolean> get() = _loadingRestaurants

    private val _restaurants = MutableLiveData<List<RestaurantView>>(listOf())
    val restaurants: LiveData<List<RestaurantView>> get() = _restaurants

    private var hasAnError = false

    init {
        loadRestaurants()
    }

    private fun loadRestaurants() {
        hasAnError = false
        viewModelScope.launch {
            repository.fetchRestaurantInfo().collect { result ->
                when (result) {
                    is NetResult.Error -> hasAnError = true
                    is NetResult.Success -> handleSuccess(result.data as RestaurantView)
                    is NetResult.StartLoading -> _loadingRestaurants.value = true
                    is NetResult.StopLoading -> handleStopLoading()
                }
            }
        }
    }

    private fun handleSuccess(restView: RestaurantView) {
        _restaurants.value = _restaurants.value?.plus(restView)
        _loadingRestaurants.value = false
    }

    private fun handleStopLoading() {
        _loadingRestaurants.value = false
        if(hasAnError && _restaurants.value?.isEmpty() == true)
            _loadingError.value = WrongState.ERROR
        else if (_restaurants.value?.isEmpty()  == true)
            _loadingError.value = WrongState.EMPTY
    }
}