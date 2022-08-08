package com.oliverbotello.hms.covidcases.ui.map

import android.util.Log
import androidx.lifecycle.*
import com.huawei.hms.network.httpclient.Callback
import com.huawei.hms.network.httpclient.Response
import com.huawei.hms.network.httpclient.Submit
import com.oliverbotello.hms.covidcases.Country
import com.oliverbotello.hms.covidcases.helpers.MoshiHelper
import com.oliverbotello.hms.covidcases.network.ApiConnection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val connection: ApiConnection = ApiConnection()
    private val isBussy: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    private val countries: MutableLiveData<Array<Country>> = MutableLiveData<Array<Country>>()
    private val urlCovidData: MutableLiveData<String> = MutableLiveData<String>("")
    private val countriesCallback: Callback<String> = object : Callback<String>() {
        override fun onResponse(submit: Submit<String>?, response: Response<String>?) {
            Log.i("Oliver404", "onResponse")
            val countriesData: Array<Country>? = MoshiHelper<Array<Country>>()
                .jsonTo(Array<Country>::class.java, response?.body.toString())

            GlobalScope.launch(Dispatchers.Main) {
                countries.value = countriesData?:arrayOf()
                isBussy.value = false
            }
        }

        override fun onFailure(submit: Submit<String>?, error: Throwable?) {
            Log.i("Oliver404", error.toString())
            GlobalScope.launch(Dispatchers.Main) {
                isBussy.value = false
            }
        }
    }
    private val covidCallback: Callback<String> = object : Callback<String>() {
        override fun onResponse(submit: Submit<String>?, response: Response<String>?) {
            Log.i("Oliver404", "onResponse")
            GlobalScope.launch(Dispatchers.Main) {
                urlCovidData.value = response?.body.toString()
            }
        }

        override fun onFailure(submit: Submit<String>?, error: Throwable?) {
            Log.i("Oliver404", error.toString())
            GlobalScope.launch(Dispatchers.Main) {
            }
        }
    }

    fun start(): Unit {
        isBussy.value = true

        connection.getCountriesInformation(countriesCallback)
    }

    fun getCovidData(country: String): Unit {
        connection.getCovidInformation(covidCallback, country.lowercase().replace(" ", "-"))
    }

    fun setIsBussyListener(lifecycle: LifecycleOwner, listener: Observer<Boolean>): Unit {
        isBussy.observe(lifecycle, listener)
    }

    fun setCountriesListener(lifecycle: LifecycleOwner, listener: Observer<Array<Country>>): Unit {
        countries.observe(lifecycle, listener)
    }

    fun setCovidListener(lifecycle: LifecycleOwner, listener: Observer<String>): Unit {
        urlCovidData.observe(lifecycle, listener)
    }
}