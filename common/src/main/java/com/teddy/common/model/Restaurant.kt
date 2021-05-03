package com.teddy.common.model

class Restaurant(
    val idRestaurant: Long,
    val name: String,
    val address: String,
    val currencyCode: String,
    val minPrice: Long,
    val minPriceBefore: Long,
    val rateCount: Long,
    val avgRate: Float?,
    val rateDistinction: String,

    val menu: List<BasicMenu>,

    val tripAdvisorAvgRating: Float?,
    val tripAdvisorReviewCount: Float?,

    val mainPics: MainPics,
    val diaporamaPics: List<DiaporamaPic>,
)

class BasicMenu(
    val name: String?,
    val price: Float?,
)

class MainPics(
    val medium: String,
    val big: String,
)

class DiaporamaPic(
    val medium: String,
)