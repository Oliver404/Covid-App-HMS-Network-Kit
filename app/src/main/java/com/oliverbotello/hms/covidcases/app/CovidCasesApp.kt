package com.oliverbotello.hms.covidcases.app

import android.app.Application
import com.huawei.hms.maps.MapsInitializer
import com.oliverbotello.hms.covidcases.R

class CovidCasesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Init maps
        MapsInitializer.setApiKey(getString(R.string.hms_api_key))
        MapsInitializer.initialize(applicationContext)
    }
}