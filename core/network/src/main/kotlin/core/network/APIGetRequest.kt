package core.network

/**
 * - Using abstract class instead of interface because interface does not allow `inline` and 'refined` keyword
 */
open class APIGetRequest {
    /**
     * - On failure return a [Throwable] where in the message parameter give the short message, and in the
     * cause parameter give the details of the cause and also give the source of exception or failure
     * - Providing dummy implementation  because abstract method does not allow `inline` and 'refined` keyword
     */
    open suspend  fun < T> request(url: String): Result<T> {
        return Result.failure(
            Throwable(
                message = "Should be implement"

            )
        )
    }

}