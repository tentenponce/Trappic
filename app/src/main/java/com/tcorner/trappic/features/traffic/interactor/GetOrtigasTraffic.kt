package com.tcorner.trappic.features.traffic.interactor

import com.tcorner.trappic.core.exception.Failure
import com.tcorner.trappic.core.interactor.Either
import com.tcorner.trappic.core.interactor.UseCaseNoParam
import com.tcorner.trappic.core.interactor.map
import com.tcorner.trappic.features.global.data.DistanceMatrixRepository
import com.tcorner.trappic.features.global.data.EdsaLocation
import com.tcorner.trappic.features.traffic.model.TrafficInfo
import javax.inject.Inject

/**
 * returns traffic information on ortigas
 *
 * Created by Exequiel Egbert V. Ponce on 11/21/2018.
 */
class GetOrtigasTraffic @Inject constructor(private val distanceMatrixRepository: DistanceMatrixRepository) :
    UseCaseNoParam<TrafficInfo>() {

    companion object {
        const val NAME = "Ortigas"
    }

    override suspend fun run(): Either<Failure, TrafficInfo> =
        distanceMatrixRepository.getDistanceMatrix(
            EdsaLocation.SANTOLAN_LAT,
            EdsaLocation.SANTOLAN_LNG,
            EdsaLocation.ORTIGAS_LAT,
            EdsaLocation.ORTIGAS_LNG
        ).map {
            it.copy(
                name = NAME,
                encodedCoordinates = EdsaLocation.ORTIGAS_POLYLINES
            )
        }
}