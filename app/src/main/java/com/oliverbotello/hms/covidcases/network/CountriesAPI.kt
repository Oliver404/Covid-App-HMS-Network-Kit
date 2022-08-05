package com.oliverbotello.hms.covidcases.network

import com.huawei.hms.network.httpclient.Submit
import com.huawei.hms.network.restclient.anno.GET
import com.huawei.hms.network.restclient.anno.Url


interface CountriesAPI {
    @GET("all")
    fun getBasicInformation(): Submit<String?>
}