package core.network.component

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.TimeoutCancellationException
/**
 * The ExceptionHandler class is responsible for handling various types of exceptions
 * that may occur during HTTP requests and providing user-friendly error messages.
 *
 * - This class wraps common HTTP and network-related exceptions into a more readable format
 *   by separating the message (a brief, user-friendly description) and the cause (a detailed
 *   explanation, such as the HTTP status code or reason for failure).
 *
 * - This separation allows developers to easily understand the nature of the error,
 *   while also retaining the original cause for debugging purposes.
 *
 * - The following exceptions are handled:
 *      - RedirectResponseException: Handles 3xx redirection errors.
 *      - ClientRequestException: Handles 4xx client-side errors.
 *      - ServerResponseException: Handles 5xx server-side errors.
 *      - TimeoutCancellationException: Handles cases where the request times out.
 *      - ConnectTimeoutException: Handles connection timeout errors (e.g., server unreachable).
 *      - IOException: Handles generic network issues such as no internet connection or I/O failures.
 *      - A fallback for unexpected exceptions, with the message and cause captured appropriately.
 *
 * Example usage:
 *
 * ```kotlin
 * catch (ex: Exception) {
 *     val exceptionHandler = ExceptionHandler()
 *     Result.failure(exceptionHandler.handleException(ex))
 * }
 * ```
 *
 * This class ensures that user-facing error messages are clear and concise, while providing
 * developers with the necessary details to debug any issues effectively.
 */

internal class ExceptionToError {
    fun convert(e: Exception): Throwable {
        return when (e) {
            is RedirectResponseException -> Throwable(
                message = "Redirection error:",
                cause = Throwable(e.response.status.description)
            )

            is ClientRequestException -> Throwable(
                message = "Client error:",
                cause = Throwable(e.response.status.description)
            )

            is ServerResponseException -> Throwable(
                message = "Server error:",
                cause = Throwable(e.response.status.description)
            )

            is TimeoutCancellationException -> Throwable(
                message = "Request timeout. Please check your connection.",
                cause = Throwable("Timeout reached")
            )

            is ConnectTimeoutException -> Throwable(
                message = "Connection timeout. Unable to reach the server.",
                cause = Throwable("Connection failed")
            )

            is IOException -> Throwable(
                message = "Network error. Please check your internet connection.",
                cause = Throwable("I/O failure or no internet connection")
            )

            else -> Throwable(
                message = "Unexpected error:",
                cause = Throwable(e.message ?: "Unknown error")
            )
        }
    }
}