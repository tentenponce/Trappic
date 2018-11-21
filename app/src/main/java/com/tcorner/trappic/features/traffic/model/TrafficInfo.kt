package com.tcorner.trappic.features.traffic.model

/**
 *
 * Created by Exequiel Egbert V. Ponce on 11/19/2018.
 */
data class TrafficInfo(
    val name: String,
    val duration: Double,
    val durationInTraffic: Double,
    val encodedCoordinates: String
) {
    constructor(
        duration: Double,
        durationInTraffic: Double
    ) :
            this("", duration, durationInTraffic, "")
}