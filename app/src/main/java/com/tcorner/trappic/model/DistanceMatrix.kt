package com.tcorner.trappic.model

import com.squareup.moshi.Json

data class DistanceMatrix(
    @field:Json(name = "destination_addresses") val destinationAddresses: List<String>?,
    @field:Json(name = "origin_addresses") val originAddresses: List<String>?,
    val rows: List<DistanceMatrixRow>?,
    val status: String?
)