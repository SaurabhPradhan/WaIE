package org.nasa.apod_domain.repository

import java.lang.UnsupportedOperationException

interface Repository<I, O> {
    suspend fun get(params: I): Result<O> = throw UnsupportedOperationException(NOT_IMPLEMENTED)
    suspend fun create(params: I): Result<O> = throw UnsupportedOperationException(NOT_IMPLEMENTED)
    suspend fun update(params: I): Result<O> = throw UnsupportedOperationException(NOT_IMPLEMENTED)
    suspend fun delete(params: I): Result<O> = throw UnsupportedOperationException(NOT_IMPLEMENTED)

    companion object {
        private const val NOT_IMPLEMENTED = "Not Implemented"
    }
}
