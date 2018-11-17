package com.tcorner.trappic.features.ui

import android.graphics.Color
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
            observe(cubaoTraffic) { /* TODO handle percentage */ }
            failure(failure, ::handleFailure)
        }

        mainViewModel.getCubaoTraffic()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val qMartLocation = LatLng(EdsaLocation.QMART_LAT, EdsaLocation.QMART_LNG)

        val line = mMap.addPolyline(
            PolylineOptions()
                .add(
                    LatLng(EdsaLocation.QMART_LAT, EdsaLocation.QMART_LNG),
                    LatLng(EdsaLocation.SANTOLAN_LAT, EdsaLocation.SANTOLAN_LNG)
                )
                .width(5f)
                .color(Color.RED)
        )

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
}
