package com.tcorner.trappic.interactor

abstract class BaseUseCaseParam<P, R> {

    abstract suspend fun execute(t: P): R
}