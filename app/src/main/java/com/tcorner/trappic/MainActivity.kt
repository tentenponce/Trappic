package com.tcorner.trappic

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.tcorner.trappic.interactor.GetCubaoTraffic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(),
    OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val getCubaoTraffic =
            GetCubaoTraffic(DistanceMatrixRepositoryImpl(DistanceMatrixClient(DistanceMatrixFactory.makeDistanceMatrixService())))

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val percentage = getCubaoTraffic.execute()
                Toast.makeText(this@MainActivity, "percentage: " + percentage, Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error: " + e.javaClass.simpleName, Toast.LENGTH_LONG).show()
            }
        }
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
}
