package com.tcorner.trappic.features.traffic.ui

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.tcorner.trappic.R
import com.tcorner.trappic.core.base.BaseActivity
import com.tcorner.trappic.core.exception.Failure
import com.tcorner.trappic.core.extension.failure
import com.tcorner.trappic.core.extension.observe
import com.tcorner.trappic.core.extension.viewModel
import com.tcorner.trappic.features.global.data.EdsaLocation
import com.tcorner.trappic.features.global.util.DateUtil
import com.tcorner.trappic.features.traffic.TrafficCalculatorFactory
import com.tcorner.trappic.features.traffic.interactor.GetCubaoTraffic
import com.tcorner.trappic.features.traffic.model.TrafficInfo

class MainActivity : BaseActivity(),
    OnMapReadyCallback {

    companion object {
        private const val POLYLINE_WIDTH = 30f
    }

    private lateinit var mMap: GoogleMap

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent.inject(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mainViewModel = viewModel(viewModelFactory) {
            observe(cubaoTraffic) {
                showCubaoLine(cubaoTraffic.value)
            }
            failure(failure, ::handleFailure)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mainViewModel.getTrafficInfo()

        /* setup lines initially */
        showCubaoLine(TrafficInfo(name = GetCubaoTraffic.NAME, duration = -1.0, durationInTraffic = -1.0))

        /* setup the camera */
        val qMartLocation = LatLng(EdsaLocation.QMART_LAT, EdsaLocation.QMART_LNG)
        mMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder()
                    .target(qMartLocation).zoom(15f).build()
            )
        )
    }

    private fun handleFailure(failure: Failure?) {
        /* TODO handle failure */
    }

    private fun showCubaoLine(cubaoTraffic: TrafficInfo?) {
        /* add line */
        mMap.addPolyline(
            PolylineOptions()
                .add(
                    LatLng(EdsaLocation.QMART_LAT, EdsaLocation.QMART_LNG),
                    LatLng(EdsaLocation.SANTOLAN_LAT, EdsaLocation.SANTOLAN_LNG)
                )
                .width(POLYLINE_WIDTH)
                .color(TrafficCalculatorFactory.buildTrafficColor(this, cubaoTraffic))
        )

        /* add marker */
        mMap.addMarker(
            buildMarker(
                LatLng(EdsaLocation.QMART_SANTOLAN_CENTER_LAT, EdsaLocation.QMART_SANTOLAN_CENTER_LNG),
                cubaoTraffic?.name ?: getString(R.string.unknown_road),
                TrafficCalculatorFactory.getDuration(cubaoTraffic),
                TrafficCalculatorFactory.getDurationInTraffic(cubaoTraffic)
            )
        ).showInfoWindow()
    }

    private fun buildMarker(latLng: LatLng, name: String, duration: Double, durationInTraffic: Double): MarkerOptions {
        return MarkerOptions()
            .position(latLng)
            .title(
                String.format(
                    "%s %smin + %smin", name,
                    if (duration < 0) 0 else buildTime(duration.toInt()),
                    if (durationInTraffic < 0) 0 else buildTime(durationInTraffic.toInt())
                )
            )
    }

    private fun buildTime(time: Int): String {
        val splittedTime = DateUtil.splitToComponentTimes(time)

        val hour = splittedTime[0]
        val min = splittedTime[1]

        var timeString = ""

        if (hour > 0) {
            timeString += hour
        }

        if (min > 0) {
            if (hour > 0) {
                timeString += " " // add space
            }

            timeString += min
        }

        return timeString
    }
}

