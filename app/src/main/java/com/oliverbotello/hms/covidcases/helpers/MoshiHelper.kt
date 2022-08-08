package com.oliverbotello.hms.covidcases.helpers

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter

class MoshiHelper<T> {
    companion object {
        private var MOSHI: Moshi? = null
    }

    init {
        if (MOSHI == null)
            MOSHI = Moshi.Builder().build()
    }

    fun jsonTo(type: Class<T>, json: String): T? {
        return MOSHI?.adapter<T>(type)?.fromJson(json)
    }
}