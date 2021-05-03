package com.teddy.datasource.local

import com.teddy.common.model.Restaurant

class RestaurantLocalSource {

    private val cache = mutableMapOf<Long, Restaurant>()

    fun addItem(key: Long, item: Restaurant) {
        if (!cache.containsKey(key)) {
            cache[key] = item
        }
    }

    fun getItemBy(key: Long): Restaurant? = if (cache.containsKey(key)) cache[key] else null
}