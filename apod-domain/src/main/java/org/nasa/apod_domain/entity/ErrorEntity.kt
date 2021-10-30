package org.nasa.apod_domain.entity

/**
 * Represents the different error types supported by Mobile Checkout.
 * @property Network - whenever any network related errors happen, such as timeouts, server not
 * found, no network connection. etc
 * @property NotFound - When an item or resource is not found as a result of an operation
 * @property InvalidInput - The input given was invalid
 * @property RequestFailure - When there is a business reason for the request not being successful.
 * @property AuthenticationFailure - When authentication is invalid.
 * @property Unknown - ponder your life choices
 */
abstract class ErrorEntity {
    object Network : ErrorEntity()
    object NotFound : ErrorEntity()
    object InvalidInput : ErrorEntity()
    object RequestFailure : ErrorEntity()
    object AuthenticationFailure : ErrorEntity()
    object Unknown : ErrorEntity()

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}