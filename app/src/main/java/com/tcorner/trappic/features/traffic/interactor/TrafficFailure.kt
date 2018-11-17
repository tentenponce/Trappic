package com.tcorner.trappic.features.traffic.interactor

import com.tcorner.trappic.core.exception.Failure

/**
 *
 * Created by Exequiel Egbert V. Ponce on 11/17/2018.
 */
class TrafficFailure {
    class NullDuration : Failure.FeatureFailure()
    class NullDurationTraffic : Failure.FeatureFailure()
}