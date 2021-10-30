package org.nasa.apod_domain

interface ErrorHandler {
    /**
     * Handles Throwable and transforms them to to existing errors supported by Mobile Checkout.
     * @property [throwable] [Throwable] Throwable or Http Response codes to evaluate for
     * transformation.
     * @return a result error [Result.Error] containing an [ErrorEntity] detailing the different
     * error types supported by Mobile Checkout.
     */
    fun getError(throwable: Throwable): Result.Error
}
