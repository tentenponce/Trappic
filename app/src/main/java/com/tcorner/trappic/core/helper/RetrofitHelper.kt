package com.tcorner.trappic.core.helper

import com.tcorner.trappic.core.interactor.Either
import retrofit2.Call

object RetrofitHelper {

    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Exception, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(Exception())
            }
        } catch (exception: Throwable) {
            Either.Left(Exception())
        }
    }
}