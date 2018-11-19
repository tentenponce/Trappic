package com.tcorner.trappic.features.traffic

import android.content.Context
import com.tcorner.trappic.features.traffic.model.TrafficInfo

/**
 *
 * Created by Exequiel Egbert V. Ponce on 11/19/2018.
 */
object TrafficCalculatorFactory {

    /**
     * build the traffic color
     */
    fun buildTrafficColor(context: Context, cubaoTraffic: TrafficInfo?) =
        TrafficCalculator.getTrafficColor(
            context,
            TrafficCalculator.getTrafficPercentage(
                getDuration(cubaoTraffic),
                getDurationInTraffic(cubaoTraffic)
            )
        )

    fun getDuration(cubaoTraffic: TrafficInfo?) = cubaoTraffic?.duration ?: 0.0
    fun getDurationInTraffic(cubaoTraffic: TrafficInfo?) = cubaoTraffic?.durationInTraffic ?: 0.0
}