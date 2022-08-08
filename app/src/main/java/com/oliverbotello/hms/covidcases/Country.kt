package com.oliverbotello.hms.covidcases

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Country(
    val name: Name,
    val latlng: DoubleArray = doubleArrayOf(0.0, 0.0),
    val population: Int = 0,
    val flags: Flag
)
