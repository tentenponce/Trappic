package com.tcorner.trappic.interactor

abstract class BaseUseCase<R> {

    abstract suspend fun execute(): R
}