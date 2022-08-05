package com.oliverbotello.hms.covidcases.ui.map

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.huawei.hms.maps.*
import com.oliverbotello.hms.covidcases.R

class MapFragment : Fragment(), OnMapReadyCallback, Observer<Boolean> {
    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var map: HuaweiMap
    private lateinit var viewModel: MapViewModel

    /*
    * Fragment
    * */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_map, container, false)
        val mMapView: MapView = root.findViewById(R.id.mapvw)
        var mapViewBundle: Bundle? = null

        if (savedInstanceState != null)
            mapViewBundle = savedInstanceState.getBundle("MapViewBundleKey")

        mMapView.onCreate(mapViewBundle)
        mMapView.getMapAsync(this)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        // Set all observers and listeners
        viewModel.setIsBussyListener(viewLifecycleOwner, this)

        viewModel.start()
    }

    /*
    * OnMapReadyCallback
    * */
    override fun onMapReady(map: HuaweiMap) {
        this.map = map
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
        this.view?.findViewById<View>(R.id.loadingvw)?.isVisible = show
    }
}