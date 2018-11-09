package com.tcorner.trappic

import com.tcorner.trappic.model.DistanceMatrix

interface DistanceMatrixRepository {

    suspend fun getDistanceDetails(origin: String, destination: String): DistanceMatrix?
}