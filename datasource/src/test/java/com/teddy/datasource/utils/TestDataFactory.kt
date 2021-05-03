package com.teddy.datasource.utils

import kotlin.random.Random

val randomId: Int get() = Random.nextInt(0, 100)

fun randomNum(amount: Int) =  Random.nextInt(1, amount)
fun randomNumbers(amount: Int): List<Int> =  (0..amount).toList().map { Random.nextInt(1, amount * 100) }

class TestDataFactory<T>(private val generator: (index: Int) -> T) {

    val singleRecord: T get() = this.generator(0)
    fun singleRecord(fixedId: Int): T = this.generator(fixedId)

    fun manyRecords(amount: Int = 10): List<T> {
        return (1..amount).toList().map { index -> this.generator(index) }
    }
}