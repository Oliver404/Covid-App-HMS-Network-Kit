package com.oliverbotello.hms.covidcases.network

import com.huawei.hms.network.httpclient.Submit
import com.huawei.hms.network.restclient.anno.GET
import com.huawei.hms.network.restclient.anno.Query
import com.huawei.hms.network.restclient.anno.Url
import com.oliverbotello.hms.covidcases.Country
import org.json.JSONArray

interface CovidAPI {
    @GET("countries")
    fun getCountriesInformation(): Submit<String>

    @GET("covid")
    fun getCovidInformation(@Query("country") country: String): Submit<String>
}