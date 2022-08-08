package com.oliverbotello.hms.covidcases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.oliverbotello.hms.covidcases.ui.map.MapFragment

class MainActivity : AppCompatActivity(), Observer<Boolean> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MapFragment.newInstance())
                .commitNow()
        }
    }

    /*
    * Observer<Boolean>
    * */
    /**
     * Listener to display a loading view
     *
     * @param show if is true view will display a loading view
     **/
    override fun onChanged(show: Boolean) {
        this.findViewById<View>(R.id.loadingvw)?.isVisible = show
    }
}