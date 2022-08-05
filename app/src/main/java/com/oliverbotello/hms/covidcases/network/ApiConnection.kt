package com.oliverbotello.hms.covidcases.network

import android.content.Context
import com.huawei.hms.network.httpclient.Callback
import com.huawei.hms.network.httpclient.HttpClient
import com.huawei.hms.network.restclient.RestClient
import com.huawei.hms.utils.IOUtils
import com.oliverbotello.hms.covidcases.R
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.*


class ApiConnection {
    companion object {
        var COUNTRIES_CONNECTION: CovidAPI? = null
    }

    constructor() {
        if (COUNTRIES_CONNECTION == null) {
            val restClient = RestClient.Builder()
                .httpClient(HttpClient.Builder().build())
//                .baseUrl("https://api.covid19api.com/")
                .build()

            // https://datos.gob.mx/blog/catalogo-apidatosgobmx?category=api-cdn&tag=nula
            // https://restcountries.com/
            // https://api.covid19api.com/summary

            COUNTRIES_CONNECTION = restClient.create(CovidAPI::class.java)
        }
    }

    fun getBasicInformation(callback: Callback<String?>): Unit {
        COUNTRIES_CONNECTION?.let {
            it.getSummary().enqueue(callback)
        }
    }
}