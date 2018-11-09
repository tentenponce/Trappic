package com.tcorner.trappic

import com.tcorner.trappic.model.DistanceMatrix
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DistanceMatrixService {

    @GET("json")
    fun getDistanceMatrix(
        @Query("origins") origins: String,
        @Query("destinations") destinations: String,
        @Query("key") key: String,
        @Query("mode") mode: String,
        @Query("departure_time") departureTime: String,
        @Query("traffic_model") trafficModel: String
    ): Deferred<Response<DistanceMatrix>>
}