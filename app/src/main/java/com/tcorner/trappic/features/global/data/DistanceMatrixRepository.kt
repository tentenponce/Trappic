package com.tcorner.trappic.features.global.data

import com.tcorner.trappic.BuildConfig
import com.tcorner.trappic.core.exception.Failure
import com.tcorner.trappic.core.interactor.Either
import com.tcorner.trappic.features.traffic.model.DistanceMatrix
import javax.inject.Inject

/**
 *
 * Created by Exequiel Egbert V. Ponce on 11/17/2018.
 */
interface DistanceMatrixRepository {

    companion object {
        const val GOOGLE_MAPS_KEY = BuildConfig.GOOGLE_MAPS_KEY

        const val TRAVEL_MODE = "driving"
        const val DEPARTURE_TIME = "now"
        const val TRAFFIC_MODEL = "pessimistic"
    }

    fun getDistanceMatrix(
        origins: String,
        destinations: String
    ): Either<Failure, DistanceMatrix>

    class DistanceMatrixRepositoryImpl @Inject constructor(private val distanceMatrixClient: DistanceMatrixClient) :
        DistanceMatrixRepository {

        override fun getDistanceMatrix(
            origins: String,
            destinations: String
        ): Either<Failure, DistanceMatrix> {
            return distanceMatrixClient.getDistanceMatrix(
                origins = origins,
                destinations = destinations,
                key = GOOGLE_MAPS_KEY,
                mode = TRAVEL_MODE,
                departureTime = DEPARTURE_TIME,
                trafficModel = TRAFFIC_MODEL
            )
        }
    }
}