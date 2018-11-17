package com.tcorner.trappic.core.interactor

import com.tcorner.trappic.core.exception.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Use Case without Param
 *
 * Created by Exequiel Egbert V. Ponce on 11/17/2018.
 */
abstract class UseCaseNoParam<out Type> where Type : Any {

    abstract suspend fun run(): Either<Failure, Type>

    operator fun invoke(onResult: (Either<Failure, Type>) -> Unit) {
        val job = GlobalScope.async { run() }
        GlobalScope.launch(Dispatchers.Main) { onResult(job.await()) }
    }
}