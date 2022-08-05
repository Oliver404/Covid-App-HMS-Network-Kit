package com.oliverbotello.hms.covidcases.network

import com.huawei.hms.network.httpclient.Submit
import com.huawei.hms.network.restclient.anno.GET
import com.huawei.hms.network.restclient.anno.Url

interface CovidAPI {
    @GET
    fun getSummary(@Url url: String = "https://api.datos.gob.mx/v1/calidadAire"): Submit<String?>
}