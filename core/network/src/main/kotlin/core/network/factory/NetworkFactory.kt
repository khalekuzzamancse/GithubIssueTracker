@file:Suppress("UnUsed")
package core.network.factory

import core.network.component.ApiServiceClient
import core.network.component.JsonParser

/**
 * - Contains the `Factory method` ,it ensure `Single source of truth` for object creation which helps centralize
 * and manage the instantiation logic in one place.
 *  - HTTP client or modifying internal behavior) can be made here, without affecting the client code.
 *  - This promotes loose coupling between the client code and the underlying network service, improving maintainability and scalability.
 *  - The factory abstracts away the implementation details, providing a consistent interface to the client.
 */
object NetworkFactory {
    /**
     * Factory method to create an instance of [ApiServiceClient].
     * - Ensures a `single source of truth` for creating the `ApiServiceClient`,
     * - By using this factory method, any future changes to the implementation (e.g., switching to a different
     * - Clients consuming this method will not be affected if the implementation of `ApiServiceClient` changes.
     * @return An instance of [ApiServiceClient], currently implemented by [ApiServiceClientImpl].
     */

    fun createAPIServiceClient(): ApiServiceClient = ApiServiceClientImpl()

    /**
     * Factory method to create an instance of [JsonParser].
     *
     * - Provides a centralized method to create and configure the `JsonParser`, ensuring consistency across the application.
     * - This method encapsulates the internal implementation details of the JSON parsing strategy,
     *   allowing you to change the underlying parser (e.g., switching from Kotlinx Serialization to Gson or Moshi)
     *   without affecting the client code that relies on this parser.
     * - By using this factory method, you ensure that clients are decoupled from the actual implementation of the parser.
     *
     * @return An instance of [JsonParser], currently implemented by [JsonParserImpl].
     */
    fun createJsonParser():JsonParser=JsonParserImpl()
}