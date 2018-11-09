package com.tcorner.trappic.exception

class UnhandledStatusException(
    message: String,
    val code: Int
) : ServerException(message)
