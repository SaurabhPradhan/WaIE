package org.nasa.apod_domain

import org.nasa.apod_domain.entity.ErrorEntity

/**
 * Provides a mechanism for encapsulating Results from operations in a consistent manner.
 * When the operation is Successful, the operation will be of Type `Result.Success` and
 * `Result.Error` otherwise.
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: ErrorEntity) : Result<Nothing>()
}
