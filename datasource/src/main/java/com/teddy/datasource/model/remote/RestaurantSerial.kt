package com.teddy.datasource.model.remote

import com.google.gson.annotations.SerializedName
import com.teddy.common.model.BasicMenu
import com.teddy.common.model.DiaporamaPic
import com.teddy.common.model.MainPics
import com.teddy.common.model.Restaurant

class RestaurantSerial(
    @SerializedName("id_restaurant")
    val idRestaurant: Long,

    val name: String,

    val address: String,

    @SerializedName("currency_code")
    val currencyCode: String,

    @SerializedName("min_price")
    val minPrice: Long,

    @SerializedName("min_price_before")
    val minPriceBefore: Long,

    @SerializedName("rate_count")
    val rateCount: Long,

    @SerializedName("avg_rate")
    val avgRate: Float?,

    @SerializedName("rate_distinction")
    val rateDistinction: String?,

    @SerializedName("card_start_1")
    val cardStart1: String?,

    @SerializedName("card_start_2")
    val cardStart2: String?,

    @SerializedName("card_start_3")
    val cardStart3: String?,

    @SerializedName("card_main_1")
    val cardMain1: String?,

    @SerializedName("card_main_2")
    val cardMain2: String?,

    @SerializedName("card_main_3")
    val cardMain3: String?,

    @SerializedName("card_dessert_1")
    val cardDessert1: String?,

    @SerializedName("card_dessert_2")
    val cardDessert2: String?,

    @SerializedName("card_dessert_3")
    val cardDessert3: String?,

    @SerializedName("price_card_start_1")
    val priceCardStart1: Float?,

    @SerializedName("price_card_start_2")
    val priceCardStart2: Float?,

    @SerializedName("price_card_start_3")
    val priceCardStart3: Float?,

    @SerializedName("price_card_main_1")
    val priceCardMain1: Float?,

    @SerializedName("price_card_main_2")
    val priceCardMain2: Float?,

    @SerializedName("price_card_main_3")
    val priceCardMain3: Float?,

    @SerializedName("price_card_dessert_1")
    val priceCardDessert1: Float?,

    @SerializedName("price_card_dessert_2")
    val priceCardDessert2: Float?,

    @SerializedName("price_card_dessert_3")
    val priceCardDessert3: Float?,

    @SerializedName("trip_advisor_avg_rating")
    val tripAdvisorAvgRating: Float?,

    @SerializedName("trip_advisor_review_count")
    val tripAdvisorReviewCount: Float?,

    @SerializedName("pics_main")
    val mainPicSerial: MainPicSerial,

    @SerializedName("pics_diaporama")
    val diaporamaPics: List<DiaporamaPicSerial>
) {
    fun parse(): Restaurant = Restaurant(
        idRestaurant = idRestaurant,
        name = name,
        address = address,
        currencyCode = currencyCode,
        minPrice = minPrice,
        minPriceBefore = minPriceBefore,
        rateCount = rateCount,
        avgRate = avgRate,
        rateDistinction = rateDistinction ?: "No Rated",
        menu = buildMenu(),

        tripAdvisorAvgRating = tripAdvisorAvgRating,
        tripAdvisorReviewCount = tripAdvisorReviewCount,

        mainPics = mainPicSerial.parse(),
        diaporamaPics = diaporamaPics.map { it.parse() }.toList()
    )

    private fun buildMenu(): List<BasicMenu> = mutableListOf(
        cardStart1 to priceCardStart1,
        cardStart2 to priceCardStart2,
        cardStart3 to priceCardStart3,
        cardMain1 to priceCardMain1,
        cardMain2 to priceCardMain2,
        cardMain3 to priceCardMain3,
        cardDessert1 to priceCardDessert1,
        cardDessert2 to priceCardDessert2,
        cardDessert3 to priceCardDessert3
    ).filter { it.first != null }.map { BasicMenu(name = it.first, price = it.second) }.toList()

}

class MainPicSerial(
    @SerializedName("160x120")
    val mediumSizePic: String,

    @SerializedName("480x270")
    val bigSizePoc: String,
) {
    fun parse(): MainPics = MainPics(mediumSizePic, bigSizePoc)
}

class DiaporamaPicSerial(
    @SerializedName("480x270")
    val mediumSizePic: String,
) {
    fun parse(): DiaporamaPic = DiaporamaPic(mediumSizePic)
}