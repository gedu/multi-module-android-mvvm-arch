package com.teddy.datasource.models

import com.teddy.datasource.model.remote.MainPicSerial
import com.teddy.datasource.model.remote.RestaurantSerial
import com.teddy.datasource.utils.TestDataFactory

val mockedRestaurantSerials = TestDataFactory { id ->
    RestaurantSerial(

        idRestaurant = id.toLong(),

        name = "Res $id",

        address = "Address $id",

        currencyCode = "Currency-$id",

        minPrice = id.toLong(),

        minPriceBefore = id.toLong(),

        rateCount = id.toLong(),

        avgRate = id.toFloat(),

        rateDistinction = if (id % 2 == 0) "Great" else null,

        cardStart1 = if (id % 2 == 0) "$id" else null,

        cardStart2 = if (id % 2 == 0) "$id" else null,

        cardStart3 = if (id % 2 == 0) "$id" else null,

        cardMain1 = if (id % 2 == 0) "$id" else null,

        cardMain2 = if (id % 2 == 0) "$id" else null,

        cardMain3 = if (id % 2 == 0) "$id" else null,

        cardDessert1 = if (id % 2 == 0) "$id" else null,

        cardDessert2 = if (id % 2 == 0) "$id" else null,

        cardDessert3 = if (id % 2 == 0) "$id" else null,

        priceCardStart1 = if (id % 2 == 0) id.toFloat() else null,

        priceCardStart2 = if (id % 2 == 0) id.toFloat() else null,

        priceCardStart3 = if (id % 2 == 0) id.toFloat() else null,

        priceCardMain1 = if (id % 2 == 0) id.toFloat() else null,

        priceCardMain2 = if (id % 2 == 0) id.toFloat() else null,

        priceCardMain3 = if (id % 2 == 0) id.toFloat() else null,

        priceCardDessert1 = if (id % 2 == 0) id.toFloat() else null,

        priceCardDessert2 = if (id % 2 == 0) id.toFloat() else null,

        priceCardDessert3 = if (id % 2 == 0) id.toFloat() else null,

        tripAdvisorAvgRating = id.toFloat(),

        tripAdvisorReviewCount = id.toFloat(),

        mainPicSerial = MainPicSerial("$id", "$id"),

        diaporamaPics = listOf()
    )
}