package com.tcorner.trappic.core.helper

import com.tcorner.trappic.core.exception.Failure
import com.tcorner.trappic.core.interactor.Either
import com.tcorner.trappic.core.util.NetworkUtil
import retrofit2.Call

object RetrofitHelper {

    fun <R> handleServerResponse(call: Call<R>, networkUtil: NetworkUtil): Either<Failure, R> {
        return when (networkUtil.isConnected()) {
            true -> try {
                val response = call.execute()
                when (response.code()) {
                    200 -> when {
                        response.body() != null -> Either.Right(response.body()!!)
                        else -> Either.Left(Failure.UnexpectedServerResponse())
                    }
                    400 -> Either.Left(Failure.BadRequest())
                    else -> Either.Left(Failure.UnknownServerCode())
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError(exception))
            }
            else -> Either.Left(Failure.NetworkConnection())
        }
    }
}