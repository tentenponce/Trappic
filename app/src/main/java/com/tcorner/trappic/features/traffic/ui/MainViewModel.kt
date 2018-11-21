package com.tcorner.trappic.features.traffic.ui

import android.arch.lifecycle.MutableLiveData
import com.tcorner.trappic.core.base.BaseViewModel
import com.tcorner.trappic.features.traffic.interactor.GetCubaoTraffic
import com.tcorner.trappic.features.traffic.interactor.GetOrtigasTraffic
import com.tcorner.trappic.features.traffic.model.TrafficInfo
import javax.inject.Inject

/**
 *
 * Created by Exequiel Egbert V. Ponce on 11/17/2018.
 */
class MainViewModel
@Inject constructor(
    private val getCubaoTraffic: GetCubaoTraffic,
    private val getOrtigasTraffic: GetOrtigasTraffic
) : BaseViewModel() {

    var cubaoTraffic: MutableLiveData<TrafficInfo> = MutableLiveData()
    var ortigasTraffic: MutableLiveData<TrafficInfo> = MutableLiveData()

    fun getTrafficInfo() {
        getCubaoTraffic { it.either(::handleFailure, ::setCubaoTraffic) }
        getOrtigasTraffic { it.either(::handleFailure, ::setOrtigasTraffic) }
    }

    private fun setCubaoTraffic(cubaoTraffic: TrafficInfo) {
        this.cubaoTraffic.value = cubaoTraffic
    }

    private fun setOrtigasTraffic(ortigasTraffic: TrafficInfo) {
        this.ortigasTraffic.value = ortigasTraffic
    }
}