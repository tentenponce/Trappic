package com.tcorner.trappic.features.global.data

import com.tcorner.trappic.features.model.DistanceMatrix
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * Created by Exequiel Egbert V. Ponce on 11/13/2018.
 */
internal interface DistanceMatrixService {

    @GET("json")
    fun getDistanceMatrix(
        @Query("origins") origins: String,
        @Query("destinations") destinations: String,
        @Query("key") key: String,
        @Query("mode") mode: String,
        @Query("departure_time") departureTime: String,
        @Query("traffic_model") trafficModel: String
    ): Call<DistanceMatrix>
}