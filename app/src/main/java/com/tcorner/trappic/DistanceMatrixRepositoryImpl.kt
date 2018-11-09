package com.tcorner.trappic

import com.tcorner.trappic.model.DistanceMatrix

class DistanceMatrixRepositoryImpl constructor(val mClient: DistanceMatrixClient) :
    DistanceMatrixRepository {

    companion object {

        const val GOOGLE_MAPS_KEY = BuildConfig.GOOGLE_MAPS_KEY

        const val TRAVEL_MODE = "driving"
        const val TRAFFIC_MODEL = "pessimistic"
        const val DEPARTURE_TIME = "now"
    }

    override suspend fun getDistanceDetails(origin: String, destination: String): DistanceMatrix? {
        return mClient.getDistanceMatrix(
            origins = origin,
            destinations = destination,
            key = GOOGLE_MAPS_KEY,
            mode = TRAVEL_MODE,
            trafficModel = TRAFFIC_MODEL,
            departureTime = DEPARTURE_TIME
        )
    }
}