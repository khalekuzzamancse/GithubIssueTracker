@file:Suppress("VariableName", "UnUsed")

package issue_details.data.factory

import core.network.factory.NetworkFactory
import issue_details.data.data_source.IssueDetailsServiceFacade

/**
 * Provides `Factory methods` for this module to ensure a `single source of truth` for object creation.
 *
 * - Centralizes and manages instantiation logic, allowing for streamlined object creation in one place.
 * - Enables easy swapping of different implementations without impacting client code, promoting flexibility.
 * - Enhances loose coupling between client code and underlying services, improving maintainability and scalability.
 * - Abstracts away implementation details, ensuring a consistent and simplified interface for clients.
 */

internal object Factory {
    private const val BASE_URL = "https://api.github.com/repos/flutter/flutter"

    fun createIssueDetailsServiceFacade(): IssueDetailsServiceFacade =
        IssueDetailsServiceFacadeImpl(
            apiClient = NetworkFactory.createAPIServiceClient(),
            jsonParser = NetworkFactory.createJsonParser()
        )

    /** Builds a URL for querying issues details*/
    fun createDetailsURL(issueNo: String) = "$BASE_URL/issues/$issueNo"
    /** Builds a URL for querying issues details*/
    fun createCommentURL(issueNo: String) = "https://api.github.com/repos/flutter/flutter/issues/$issueNo/comments"

}