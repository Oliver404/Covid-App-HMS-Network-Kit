package com.oliverbotello.hms.covidcases.ui.map

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.huawei.hms.maps.*
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.Marker
import com.huawei.hms.maps.model.MarkerOptions
import com.huawei.hms.network.httpclient.Callback
import com.huawei.hms.network.httpclient.Response
import com.huawei.hms.network.httpclient.Submit
import com.oliverbotello.hms.covidcases.Country
import com.oliverbotello.hms.covidcases.R
import com.oliverbotello.hms.covidcases.network.ApiConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray

class MapFragment : Fragment(), OnMapReadyCallback, Observer<Array<Country>> {
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
        viewModel.setIsBussyListener(viewLifecycleOwner, this.activity as Observer<Boolean>)
        viewModel.setCountriesListener(viewLifecycleOwner, this)

        viewModel.start()
    }

    /*
    * OnMapReadyCallback
    * */
    override fun onMapReady(hMap: HuaweiMap) {
        map = hMap

        map.setInfoWindowAdapter(CustomInfoWindowAdapter())
    }

    /*
    * Observer<JSONArray>
    * */
    /**
     * Listener to show pins
     *
     * @param countries list of countries to show in the map
     **/
    override fun onChanged(countries: Array<Country>?) {
        countries?.let {
            for(i in countries.indices) {
                val country = countries[i]
                val options = MarkerOptions()
                    .position(LatLng(country.latlng[0], country.latlng[1]))
                    .title(country.name.common)
                    .snippet(country.name.official)

                map.addMarker(options)
            }
        }
    }

    internal inner class CustomInfoWindowAdapter : HuaweiMap.InfoWindowAdapter, Observer<String> {
        private val mWindow: View = layoutInflater.inflate(R.layout.custom_info_window, null)

        override fun getInfoWindow(marker: Marker): View {
            val txtvTitle = mWindow.findViewById<TextView>(R.id.txtvw_common)
            val txtvSnippett = mWindow.findViewById<TextView>(R.id.txtvw_official)
            txtvTitle.text = marker.title
            txtvSnippett.text = marker.snippet
            mWindow.findViewById<AppCompatImageView>(R.id.imgvw_chart).isVisible = false
            mWindow.findViewById<AppCompatImageView>(R.id.indicator).isVisible = true

            viewModel.setCovidListener(viewLifecycleOwner, this)
            viewModel.getCovidData(marker.title)

            return mWindow
        }

        override fun getInfoContents(marker: Marker): View? {
            return null
        }

        private fun loadImage(url: String) {
            val chart = mWindow.findViewById<AppCompatImageView>(R.id.imgvw_chart)
            chart.isVisible = true
            mWindow.findViewById<AppCompatImageView>(R.id.indicator).isVisible = false

            Glide.with(mWindow).load(url).into(chart)
        }

        override fun onChanged(value: String?) {
            loadImage(value?:"")
        }

    }
}