package com.tcorner.trappic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

val asyncJobs: MutableList<Job> = mutableListOf()
val deferredObjects: MutableList<Deferred<*>> = mutableListOf()

fun launchAsync(block: suspend CoroutineScope.() -> Unit) {
    val job = GlobalScope.launch(Dispatchers.Main) { block() }

    asyncJobs.add(job)
    job.invokeOnCompletion { asyncJobs.remove(job) }
}

suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> {
    val deferred = GlobalScope.async(Dispatchers.Main) { block() }

    addDeferred(deferred)

    return deferred
}

suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T {
    return async(block).await()
}

fun <T> addDeferred(deferred: Deferred<T>) {
    deferredObjects.add(deferred)
    deferred.invokeOnCompletion { deferredObjects.remove(deferred) }
}

fun cancelAllAsync() {
    val asyncJobsSize = asyncJobs.size

    if (asyncJobsSize > 0) {
        for (i in asyncJobsSize - 1 downTo 0) {
            asyncJobs[i].cancel()
        }
    }

    val deferredObjectsSize = deferredObjects.size

    if (deferredObjectsSize > 0) {
        for (i in deferredObjectsSize - 1 downTo 0) {
            deferredObjects[i].cancel()
        }
    }
}