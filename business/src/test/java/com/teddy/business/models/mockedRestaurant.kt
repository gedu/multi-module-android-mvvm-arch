package com.teddy.business.models

import com.teddy.business.utils.TestDataFactory
import com.teddy.common.model.MainPics
import com.teddy.common.model.Restaurant

val mockedRestaurant = TestDataFactory { id ->
    Restaurant(

        idRestaurant = id.toLong(),

        name = "Res $id",

        address = "Address $id",

        currencyCode = "Currency-$id",

        minPrice = id.toLong(),

        minPriceBefore = id.toLong(),

        rateCount = id.toLong(),

        avgRate = id.toFloat(),

        menu = listOf(),

        rateDistinction = if (id % 2 == 0) "Great" else "Not Rated",

        tripAdvisorAvgRating = id.toFloat(),

        tripAdvisorReviewCount = id.toFloat(),

        mainPics = MainPics("$id", "$id"),

        diaporamaPics = listOf()
    )
}