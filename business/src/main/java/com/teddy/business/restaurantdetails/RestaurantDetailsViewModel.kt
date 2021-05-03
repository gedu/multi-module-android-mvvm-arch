package com.teddy.business.restaurantdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teddy.common.model.Restaurant
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RestaurantDetailsViewModel(private val repository: RestaurantDetailsRepository) :
    ViewModel() {

    private val _restaurantInfo: MutableLiveData<Restaurant?> = MutableLiveData(null)
    val restaurantInfo: LiveData<Restaurant?> get() = _restaurantInfo

    fun getRestaurantInfoBy(restaurantId: Long) {
        viewModelScope.launch {
            repository.getRestaurantInfoBy(restaurantId).collect { restaurant ->
                if (restaurant != null) {
                    _restaurantInfo.value = restaurant
                }
            }
        }
    }
}