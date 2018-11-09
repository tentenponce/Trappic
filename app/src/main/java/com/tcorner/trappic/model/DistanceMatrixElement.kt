package com.tcorner.trappic.model

import com.squareup.moshi.Json

data class DistanceMatrixElement(
    val distance: DistanceMatrixValue?,
    val duration: DistanceMatrixValue?,
    @field:Json(name = "duration_in_traffic") val durationInTraffic: DistanceMatrixValue?,
    val status: String?
)