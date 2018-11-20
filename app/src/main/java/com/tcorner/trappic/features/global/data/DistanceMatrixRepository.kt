package com.tcorner.trappic.features.global.data

import com.tcorner.trappic.BuildConfig
import com.tcorner.trappic.core.exception.Failure
import com.tcorner.trappic.core.interactor.Either
import com.tcorner.trappic.core.interactor.flatMap
import com.tcorner.trappic.features.traffic.interactor.GetCubaoTraffic
import com.tcorner.trappic.features.traffic.interactor.TrafficFailure
import com.tcorner.trappic.features.traffic.model.TrafficInfo
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
        originLat: Double,
        originLng: Double,
        destinationLat: Double,
        destinationLng: Double
    ): Either<Failure, TrafficInfo>

    class DistanceMatrixRepositoryImpl @Inject constructor(private val distanceMatrixClient: DistanceMatrixClient) :
        DistanceMatrixRepository {

        override fun getDistanceMatrix(
            originLat: Double,
            originLng: Double,
            destinationLat: Double,
            destinationLng: Double
        ): Either<Failure, TrafficInfo> {
            return distanceMatrixClient.getDistanceMatrix(
                origins = "$originLat, $originLng",
                destinations = "$destinationLat, $destinationLng",
                key = GOOGLE_MAPS_KEY,
                mode = TRAVEL_MODE,
                departureTime = DEPARTURE_TIME,
                trafficModel = TRAFFIC_MODEL
            ).flatMap {
                val duration: Double =
                    it.rows?.get(0)?.elements?.get(0)?.duration?.value?.toDouble()
                        ?: return@flatMap Either.Left(TrafficFailure.NullDuration()) // get travel duration or return a failure

                val durationInTraffic: Double =
                    it.rows[0].elements?.get(0)?.durationInTraffic?.value?.toDouble()
                        ?: return@flatMap Either.Left(TrafficFailure.NullDurationTraffic()) // get the travel traffic duration or return a failure

                Either.Right(
                    TrafficInfo(
                        name = GetCubaoTraffic.NAME,
                        duration = duration,
                        durationInTraffic = durationInTraffic
                    )
                )
            }
        }
    }
}