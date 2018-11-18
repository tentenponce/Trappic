package com.tcorner.trappic.features.ui

import android.arch.lifecycle.MutableLiveData
import com.tcorner.trappic.core.base.BaseViewModel
import com.tcorner.trappic.features.traffic.interactor.GetCubaoTraffic
import javax.inject.Inject

/**
 *
 * Created by Exequiel Egbert V. Ponce on 11/17/2018.
 */
class MainViewModel @Inject constructor(private val getCubaoTraffic: GetCubaoTraffic) : BaseViewModel() {

    var cubaoTraffic: MutableLiveData<Double> = MutableLiveData()

    fun getTrafficInfo() {
        getCubaoTraffic { it.either(::handleFailure, ::setCubaoTraffic) }
    }

    private fun setCubaoTraffic(cubaoTraffic: Double) {
        this.cubaoTraffic.value = cubaoTraffic
    }
}