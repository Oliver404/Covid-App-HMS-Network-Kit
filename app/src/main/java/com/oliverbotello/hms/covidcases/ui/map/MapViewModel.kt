package com.oliverbotello.hms.covidcases.ui.map

import android.app.Application
import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.*
import com.huawei.hms.network.httpclient.Callback
import com.huawei.hms.network.httpclient.Response
import com.huawei.hms.network.httpclient.Submit
import com.oliverbotello.hms.covidcases.network.ApiConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MapViewModel : ViewModel() {
    private val connection: ApiConnection = ApiConnection()
    private var isBussy: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    private val callback: Callback<String?> = object : Callback<String?>() {
        override fun onResponse(submit: Submit<String?>?, response: Response<String?>?) {
            Log.e("Oliver404", response.toString())
            GlobalScope.launch(Dispatchers.Main) {
                isBussy.value = false
            }
        }

        override fun onFailure(submit: Submit<String?>?, error: Throwable?) {
            Log.e("Oliver404", error.toString())
            GlobalScope.launch(Dispatchers.Main) {
                isBussy.value = false
            }
        }
    }

    fun start(): Unit {
        isBussy.value = true

        connection.getBasicInformation(callback)
    }

    fun setIsBussyListener(lifecycle: LifecycleOwner, listener: Observer<Boolean>): Unit {
        isBussy.observe(lifecycle, listener)
    }
}