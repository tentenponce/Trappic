package com.tcorner.trappic

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DistanceMatrixFactory {
    const val BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/"

    fun makeDistanceMatrixService(): DistanceMatrixService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(DistanceMatrixService::class.java)
    }
}