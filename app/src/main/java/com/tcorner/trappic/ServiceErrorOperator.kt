package com.tcorner.trappic

import com.tcorner.trappic.exception.UnhandledStatusException
import kotlinx.coroutines.Deferred
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

suspend fun <T> errorHandler(block: Deferred<Response<T>>): T? {
    val response = block.await()

    val code = response.code()

    if (code == HttpsURLConnection.HTTP_OK) {
        return response.body()
    } else {
        val message = "It seems that we've encountered a problem. Status: $code"

        throw UnhandledStatusException(message, code)
    }
}