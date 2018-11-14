package com.tcorner.trappic.features.traffic.data

import com.tcorner.trappic.model.DistanceMatrix
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * Created by Exequiel Egbert V. Ponce on 11/13/2018.
 */
@Singleton
class DistanceMatrixClient
@Inject constructor(private val retrofit: Retrofit) {
    private val distanceMatrixService by lazy { retrofit.create(DistanceMatrixService::class.java) }

    fun getDistanceMatrix(
        origins: String,
        destinations: String,
        key: String,
        mode: String,
        departureTime: String,
        trafficModel: String
    ): Call<Response<DistanceMatrix>> {
        return distanceMatrixService.getDistanceMatrix(
            origins = origins,
            destinations = destinations,
            key = key,
            mode = mode,
            departureTime = departureTime,
            trafficModel = trafficModel
        )
    }
}