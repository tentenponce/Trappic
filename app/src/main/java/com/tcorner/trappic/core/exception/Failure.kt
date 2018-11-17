package com.tcorner.trappic.core.exception

import com.tcorner.trappic.core.exception.Failure.FeatureFailure

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    class NetworkConnection : Failure()
    class UnexpectedServerResponse : Failure()
    class BadRequest : Failure()
    class UnknownServerCode : Failure()
    class ServerError(private val exception: Throwable) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
