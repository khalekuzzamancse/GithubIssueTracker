@file:Suppress("VariableName", "FunctionName", "UnUsed")

package issue_details.data.factory

import core.network.factory.NetworkFactory
import issue_details.data.data_source.IssueDetailsServiceFacade
import issue_details.data.repository.IssueDetailsRepositoryImpl
import issue_details.domain.repository.IssueDetailsRepository

/**
 * Provides `Factory methods` for this module to ensure a `single source of truth` for object creation.
 *
 * - Centralizes and manages instantiation logic, allowing for streamlined object creation in one place.
 * - Enables easy swapping of different implementations without impacting client code, promoting flexibility.
 * - Enhances loose coupling between client code and underlying services, improving maintainability and scalability.
 * - Abstracts away implementation details, ensuring a consistent and simplified interface for clients.
 */

object IssueDataFactory {
    private const val BASE_URL = "https://api.github.com/repos/flutter/flutter"


    /** Builds a URL for querying issues details*/
    internal fun createDetailsURL(issueNo: String) = "$BASE_URL/issues/$issueNo"

    /** Builds a URL for querying issues details*/
    internal fun createCommentURL(issueNo: String) =
        "https://api.github.com/repos/flutter/flutter/issues/$issueNo/comments"

    fun createRepository(): IssueDetailsRepository =
        IssueDetailsRepositoryImpl(_createIssueDetailsServiceFacade())

    private fun _createIssueDetailsServiceFacade(): IssueDetailsServiceFacade =
        IssueDetailsServiceFacadeImpl(
            apiClient = NetworkFactory.createAPIServiceClient(),
            jsonParser = NetworkFactory.createJsonParser()
        )

}