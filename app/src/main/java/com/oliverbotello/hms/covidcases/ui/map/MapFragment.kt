package com.oliverbotello.hms.covidcases.ui.map

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.oliverbotello.hms.covidcases.R

class MapFragment : SupportMapFragment(), OnMapReadyCallback {
    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var map: HuaweiMap
    private lateinit var viewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onCreate(intent: Bundle?) {
        super.onCreate(intent)
        this.getMapAsync(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        // TODO: Use the ViewModel
    }

    /*
    * OnMapReadyCallback
    * */
    override fun onMapReady(map: HuaweiMap) {
        this.map = map
    }
}