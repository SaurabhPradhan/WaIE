package org.nasa.apod_data

import org.nasa.apod_domain.ErrorHandler
import org.nasa.apod_domain.Result
import org.nasa.apod_domain.entity.ErrorEntity
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class GenericErrorHandler : ErrorHandler {

    /**
     * Transforms input Throwable, Exceptions or HttpException Response codes into existing error
     * objects.
     * @property [throwable] [Throwable] Throwable or Http Response codes to evaluate for
     * transformation.
     * @return a result error detailing the different error types supported by Mobile Checkout.
     */
    override fun getError(throwable: Throwable): Result.Error {
        return when (throwable) {
            is IOException -> Result.Error(ErrorEntity.Network)
            is HttpException -> when (throwable.code()) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> Result.Error(ErrorEntity.AuthenticationFailure)
                in HttpURLConnection.HTTP_BAD_REQUEST..Int.MAX_VALUE -> Result.Error(ErrorEntity.Unknown)
                else -> Result.Error(ErrorEntity.Unknown)
            }
            else -> Result.Error(ErrorEntity.Unknown)
        }
    }
}