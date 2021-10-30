package org.nasa.apod_domain.datasource

import java.lang.UnsupportedOperationException

import org.nasa.apod_domain.Result

interface Datasource<I, O> {
    /**
     * Gets a single element of type [O]
     *
     * @param parameters Input of type [I] to perform the get operation
     */
    suspend fun get(parameters: I): Result<O> =
        throw UnsupportedOperationException(NOT_IMPLEMENTED)

    /**
     * Adds a single element of type [O]
     *
     * @param parameters Input of type [I] to perform the add operation
     */
    suspend fun create(parameters: I): Result<O> =
        throw UnsupportedOperationException(NOT_IMPLEMENTED)

    /**
     * Updates and element of type [O]
     *
     * @param parameters Input of type [I] to perform the update operation
     */
    suspend fun update(parameters: I): Result<O> =
        throw UnsupportedOperationException(NOT_IMPLEMENTED)

    /**
     * Deletes an element of type [O]
     *
     * @param parameters Input of type [I] to perform the delete operation
     */
    suspend fun delete(parameters: I): Result<O> =
        throw UnsupportedOperationException(NOT_IMPLEMENTED)

    companion object {
        private const val NOT_IMPLEMENTED = "Not Implemented"
    }
}
