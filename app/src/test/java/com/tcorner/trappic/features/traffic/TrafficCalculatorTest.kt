package com.tcorner.trappic.features.traffic

import org.junit.Test

/**
 *
 * Created by Exequiel Egbert V. Ponce on 11/24/2018.
 */
class TrafficCalculatorTest {

    @Test
    fun `Should return 20 percent`() {
        assert(TrafficCalculator.getTrafficPercentage(8.0, 10.0) == 20.0)
    }
}