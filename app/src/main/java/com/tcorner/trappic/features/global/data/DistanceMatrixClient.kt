package com.tcorner.trappic.features.global.data

import com.tcorner.trappic.core.exception.Failure
import com.tcorner.trappic.core.helper.RetrofitHelper
import com.tcorner.trappic.core.interactor.Either
import com.tcorner.trappic.core.util.NetworkUtil
import com.tcorner.trappic.features.model.DistanceMatrix
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handle all the server response on client
 *
 * Created by Exequiel Egbert V. Ponce on 11/13/2018.
 */
@Singleton
class DistanceMatrixClient
@Inject constructor(
    private val retrofit: Retrofit,
    private val networkUtil: NetworkUtil
) {
    private val distanceMatrixService by lazy { retrofit.create(DistanceMatrixService::class.java) }

    fun getDistanceMatrix(
        origins: String,
        destinations: String,
        key: String,
        mode: String,
        departureTime: String,
        trafficModel: String
    ): Either<Failure, DistanceMatrix> {
        return RetrofitHelper.handleServerResponse(
            distanceMatrixService.getDistanceMatrix(
                origins = origins,
                destinations = destinations,
                key = key,
                mode = mode,
                departureTime = departureTime,
                trafficModel = trafficModel
            ), networkUtil
        )
    }
}