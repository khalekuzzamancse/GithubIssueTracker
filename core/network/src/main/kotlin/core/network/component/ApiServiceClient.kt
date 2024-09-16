package core.network.component

interface ApiServiceClient {
    /**
     * - It is `Get` request
     * - Returns the raw JSON on success.
     * - It does not return `Result<T>` because, with some third-party APIs, it’s possible that
     *   the response may not always follow the same JSON format. On success, it might return one format,
     *   while on failure (e.g., due to API limits or other reasons), it could return a different JSON format.
     *   In such cases, it's not always possible to cast the JSON to type T.
     * - It is the client's responsibility to parse the JSON based on the API's response format.
     * - On failure, it returns the possible reason for the error instead of JSON.
     *   The error will be related to fetching or network issues, not related to  JSON parsing.
    * @param url The API endpoint to request data from.
     * @return A [Result] containing the raw JSON response as a string or an error.
     */
    suspend fun retrieveJsonData(url: String): Result<String>
}
