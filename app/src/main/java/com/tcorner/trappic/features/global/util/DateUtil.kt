package com.tcorner.trappic.features.global.util

/**
 *
 * Created by Exequiel Egbert V. Ponce on 11/19/2018.
 */
object DateUtil {

    fun splitToComponentTimes(seconds: Int): IntArray {
        val hours = seconds / 3600
        var remainder = seconds - hours * 3600
        val mins = remainder / 60
        remainder -= mins * 60
        val secs = remainder

        return intArrayOf(hours, mins, secs)
    }
}