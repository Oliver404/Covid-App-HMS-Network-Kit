package com.oliverbotello.hms.covidcases

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Name(
    val common: String = "",
    val official: String = ""
)
