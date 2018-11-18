package com.tcorner.trappic.features.traffic.ui

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.tcorner.trappic.R
import com.tcorner.trappic.core.base.BaseActivity
import com.tcorner.trappic.core.exception.Failure
import com.tcorner.trappic.core.extension.failure
import com.tcorner.trappic.core.extension.observe
import com.tcorner.trappic.core.extension.viewModel
import com.tcorner.trappic.features.global.data.EdsaLocation


class MainActivity : BaseActivity(),
    OnMapReadyCallback {

    companion object {
        private const val POLYLINE_WIDTH = 30f
    }

    internal lateinit var mMap: GoogleMap

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent.inject(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mainViewModel = viewModel(viewModelFactory) {
            observe(cubaoTraffic) { showCubaoLine(getTrafficColor(cubaoTraffic.value ?: 0.0)) }
            failure(failure, ::handleFailure)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mainViewModel.getTrafficInfo()

        /* setup lines initially */
        showCubaoLine(getTrafficColor(0.0))

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

    private fun showCubaoLine(color: Int) {
        val line = mMap.addPolyline(
            PolylineOptions()
                .add(
                    LatLng(EdsaLocation.QMART_LAT, EdsaLocation.QMART_LNG),
                    LatLng(EdsaLocation.SANTOLAN_LAT, EdsaLocation.SANTOLAN_LNG)
                )
                .width(POLYLINE_WIDTH)
                .color(color)
        )
    }

    private fun getTrafficColor(percentage: Double): Int {
        return when {
            percentage >= 70 -> resources.getColor(R.color.colorHeavyTraffic)
            percentage >= 35 -> resources.getColor(R.color.colorMediumTraffic)
            percentage >= 15 -> resources.getColor(R.color.colorMildTraffic)
            else -> resources.getColor(R.color.colorNoTraffic)
        }
    }
}
