package com.oliverbotello.hms.covidcases.network

import com.huawei.hms.network.httpclient.Callback
import com.huawei.hms.network.httpclient.HttpClient
import com.huawei.hms.network.restclient.RestClient

import com.oliverbotello.hms.covidcases.utils.BASE_API_URL

class ApiConnection {
    companion object {
        var COUNTRIES_CONNECTION: CovidAPI? = null
    }

    init {
        if (COUNTRIES_CONNECTION == null) {
            val restClient = RestClient.Builder()
                .httpClient(HttpClient.Builder().build())
                .baseUrl(BASE_API_URL)
                .build()

            COUNTRIES_CONNECTION = restClient.create(CovidAPI::class.java)
        }
    }

    fun getCountriesInformation(callback: Callback<String>): Unit {
        COUNTRIES_CONNECTION?.let {
            it.getCountriesInformation().enqueue(callback)
        }
    }

    fun getCovidInformation(callback: Callback<String>, country: String): Unit {
        COUNTRIES_CONNECTION?.let {
            it.getCovidInformation(country).enqueue(callback)
        }
    }
}