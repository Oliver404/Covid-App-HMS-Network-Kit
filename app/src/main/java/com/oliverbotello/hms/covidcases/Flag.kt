package com.oliverbotello.hms.covidcases

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Flag(val url: String)
