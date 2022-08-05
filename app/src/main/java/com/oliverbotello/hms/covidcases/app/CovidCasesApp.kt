package com.oliverbotello.hms.covidcases.app

import android.app.Application
import android.util.Log
import com.huawei.hms.maps.MapsInitializer
import com.huawei.hms.network.NetworkKit
import com.oliverbotello.hms.covidcases.R

class CovidCasesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Init maps
        MapsInitializer.setApiKey(getString(R.string.hms_api_key))
        MapsInitializer.initialize(applicationContext)
        // Init Network Kit
        NetworkKit.init(this, object : NetworkKit.Callback() {
                override fun onResult(result: Boolean) {
                    if (result) {
                        // NetworkKit is initialized successfully.
                        Log.e("Oliver404", "Networkkit init success")
                    } else {
                        // Failed to initialize NetworkKit.
                        Log.e("Oliver404", " Networkkit init failed")
                    }
                }
            }
        )
    }
}