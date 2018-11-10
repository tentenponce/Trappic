package com.tcorner.trappic

import com.tcorner.trappic.model.DistanceMatrix

class DistanceMatrixClient(private val mService: DistanceMatrixService) {

    suspend fun getDistanceMatrix(
        origins: String,
        destinations: String,
        key: String,
        mode: String,
        departureTime: String,
        trafficModel: String
    ): DistanceMatrix? {
        val deferred = mService.getDistanceMatrix(origins, destinations, key, mode, departureTime, trafficModel)

        addDeferred(deferred)

        val distanceMatrix = errorHandler(deferred)

        return distanceMatrix
    }
}