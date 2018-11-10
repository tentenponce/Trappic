package com.tcorner.trappic.interactor

import com.tcorner.trappic.DistanceMatrixRepository
import com.tcorner.trappic.EdsaLocation
import com.tcorner.trappic.exception.NullDurationException
import com.tcorner.trappic.exception.NullDurationTrafficException

/**
 * returns percentage of cubao's traffic
 */
class GetCubaoTraffic constructor(
    private val distanceMatrixRepository: DistanceMatrixRepository
) : BaseUseCase<Double>() {

    override suspend fun execute(): Double {
        val distanceMatrix = distanceMatrixRepository.getDistanceDetails(
            "${EdsaLocation.QMART_LAT}, ${EdsaLocation.QMART_LNG}",
            "${EdsaLocation.SANTOLAN_LAT}, ${EdsaLocation.SANTOLAN_LNG}"
        )

        val duration: Double =
            distanceMatrix?.rows?.get(0)?.elements?.get(0)?.duration?.value?.toDouble() ?: throw NullDurationException()
        val durationInTraffic: Double =
            distanceMatrix.rows[0].elements?.get(0)?.durationInTraffic?.value?.toDouble()
                ?: throw NullDurationTrafficException()

        return ((durationInTraffic - duration) / duration) * 100
    }
}