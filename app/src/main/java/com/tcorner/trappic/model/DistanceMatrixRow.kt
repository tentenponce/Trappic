package com.tcorner.trappic.model

import com.squareup.moshi.Json

data class DistanceMatrixRow(@field:Json(name = "elements") val elements: List<DistanceMatrixElement>?)