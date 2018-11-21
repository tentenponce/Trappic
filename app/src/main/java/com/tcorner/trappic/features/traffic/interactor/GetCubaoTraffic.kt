package com.tcorner.trappic.features.traffic.interactor

import com.tcorner.trappic.core.exception.Failure
import com.tcorner.trappic.core.interactor.Either
import com.tcorner.trappic.core.interactor.UseCaseNoParam
import com.tcorner.trappic.features.global.data.DistanceMatrixRepository
import com.tcorner.trappic.features.global.data.EdsaLocation
import com.tcorner.trappic.features.traffic.model.TrafficInfo
import javax.inject.Inject

/**
 * returns percentage of cubao's traffic
 */
class GetCubaoTraffic @Inject constructor(private val distanceMatrixRepository: DistanceMatrixRepository) :
    UseCaseNoParam<TrafficInfo>() {

    companion object {
        const val NAME = "Cubao"
    }

    override suspend fun run(): Either<Failure, TrafficInfo> =
        distanceMatrixRepository.getDistanceMatrix(
            EdsaLocation.QMART_LAT,
            EdsaLocation.QMART_LNG,
            EdsaLocation.SANTOLAN_LAT,
            EdsaLocation.SANTOLAN_LNG
        )
}