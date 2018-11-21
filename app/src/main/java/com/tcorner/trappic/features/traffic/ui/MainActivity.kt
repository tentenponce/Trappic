package com.tcorner.trappic.features.traffic.ui

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.tcorner.trappic.R
import com.tcorner.trappic.core.base.BaseActivity
import com.tcorner.trappic.core.exception.Failure
import com.tcorner.trappic.core.extension.failure
import com.tcorner.trappic.core.extension.observe
import com.tcorner.trappic.core.extension.viewModel
import com.tcorner.trappic.features.global.data.EdsaLocation
import com.tcorner.trappic.features.global.util.DateUtil
import com.tcorner.trappic.features.traffic.TrafficCalculator
import com.tcorner.trappic.features.traffic.TrafficCalculatorFactory
import com.tcorner.trappic.features.traffic.interactor.GetCubaoTraffic
import com.tcorner.trappic.features.traffic.model.TrafficInfo

class MainActivity : BaseActivity(),
    OnMapReadyCallback {

    companion object {
        private const val POLYLINE_WIDTH = 30f
    }

    private lateinit var mMap: GoogleMap

    private var mPolyLines = hashMapOf<String, Polyline>()
    private var mMarkers = hashMapOf<String, Marker>()

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
                showTrafficInfo(cubaoTraffic.value!!)
            }
            failure(failure, ::handleFailure)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mainViewModel.getTrafficInfo()

        /* setup lines initially */
        showTrafficInfo(
            TrafficInfo(
                name = GetCubaoTraffic.NAME,
                duration = -1.0,
                durationInTraffic = -1.0,
                originLat = EdsaLocation.QMART_LAT,
                originLng = EdsaLocation.QMART_LNG,
                destinationLat = EdsaLocation.SANTOLAN_LAT,
                destinationLng = EdsaLocation.SANTOLAN_LNG
            )
        )

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

    private fun showTrafficInfo(trafficInfo: TrafficInfo) {
        if (mPolyLines[trafficInfo.name] == null) {
            /* add line */
            val polyline = mMap.addPolyline(
                buildPolyline(
                    LatLng(trafficInfo.originLat, trafficInfo.originLng),
                    LatLng(trafficInfo.destinationLat, trafficInfo.destinationLng),
                    TrafficCalculatorFactory.buildTrafficColor(this, trafficInfo)
                )
            )

            mPolyLines[trafficInfo.name] = polyline
        }

        if (mMarkers[trafficInfo.name] == null) {
            /* add marker */
            val marker = mMap.addMarker(
                buildMarker(
                    TrafficCalculator.getCenterOfTwoCoordinates(
                        originLat = trafficInfo.originLat,
                        originLng = trafficInfo.originLng,
                        destinationLat = trafficInfo.destinationLat,
                        destinationLng = trafficInfo.destinationLng
                    ),
                    trafficInfo.name,
                    TrafficCalculatorFactory.getDuration(trafficInfo),
                    TrafficCalculatorFactory.getDurationInTraffic(trafficInfo)
                )
            )

            marker.showInfoWindow() // show marker

            mMarkers[trafficInfo.name] = marker
        } else {
            val marker = mMarkers[trafficInfo.name]!!

            marker.title = buildDuration(trafficInfo.name, trafficInfo.duration)
            marker.snippet = buildTrafficDuration(trafficInfo.durationInTraffic)

            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
                marker.showInfoWindow()
            }
        }
    }

    private fun buildPolyline(origin: LatLng, departure: LatLng, color: Int) =
        PolylineOptions()
            .add(origin, departure)
            .width(POLYLINE_WIDTH)
            .color(color)

    private fun buildMarker(latLng: LatLng, name: String, duration: Double, durationInTraffic: Double) =
        MarkerOptions()
            .position(latLng)
            .title(buildDuration(name, duration))
            .snippet(buildTrafficDuration(durationInTraffic))
            .flat(true)

    private fun buildDuration(name: String, duration: Double) =
        String.format(
            "%s %smin", name,
            if (duration < 0) 0 else buildTime(duration.toInt())
        )

    private fun buildTrafficDuration(durationInTraffic: Double) =
        String.format(
            "+ %smin on Traffic",
            if (durationInTraffic < 0) 0 else buildTime(durationInTraffic.toInt())
        )

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

