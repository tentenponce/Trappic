package com.tcorner.trappic.features.traffic

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.tcorner.trappic.R

/**
 *
 * Created by Exequiel Egbert V. Ponce on 11/19/2018.
 */
object TrafficCalculator {

    /**
     * return the color base on the traffic percentage,
     * if supplied is negative, it will return white.
     */
    @Suppress("DEPRECATION")
    fun getTrafficColor(context: Context, percentage: Double): Int {
        return when {
            percentage >= 70 -> context.resources.getColor(R.color.colorHeavyTraffic)
            percentage >= 35 -> context.resources.getColor(R.color.colorMediumTraffic)
            percentage >= 15 -> context.resources.getColor(R.color.colorMildTraffic)
            percentage >= 0 -> context.resources.getColor(R.color.colorNoTraffic)
            else -> context.resources.getColor(android.R.color.white)
        }
    }

    /**
     * compute percentage base on traffic.
     *
     * return 0 if result is negative
     */
    fun getTrafficPercentage(duration: Double, durationInTraffic: Double): Double {
        val percentage = ((durationInTraffic - duration) / duration) * 100

        return if (percentage < 0) 0.0 else percentage
    }

    fun getCenterOfTwoCoordinates(latLngs: List<LatLng>): LatLng {
        var totalLatitude = 0.0
        var totalLongitude = 0.0

        for (latLng in latLngs) {
            totalLatitude += latLng.latitude
            totalLongitude += latLng.longitude
        }

        return LatLng(totalLatitude / latLngs.size, totalLongitude / latLngs.size)
    }
}