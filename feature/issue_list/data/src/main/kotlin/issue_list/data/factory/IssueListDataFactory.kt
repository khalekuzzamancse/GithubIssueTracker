@file:Suppress("VariableName", "UnUsed")

package issue_list.data.factory

import core.network.factory.NetworkFactory
import issue_list.data.data_source.IssueQueryServiceFacade
import issue_list.data.repository.IssueListRepositoryImpl
import issue_list.domain.repository.IssueListRepository
import issue_list.domain.repository.QueryType

/**
 * Provides `Factory methods` for this module to ensure a `single source of truth` for object creation.
 *
 * - Centralizes and manages instantiation logic, allowing for streamlined object creation in one place.
 * - Enables easy swapping of different implementations without impacting client code, promoting flexibility.
 * - Enhances loose coupling between client code and underlying services, improving maintainability and scalability.
 * - Abstracts away implementation details, ensuring a consistent and simplified interface for clients.
 */

object IssueListDataFactory {
    private const val ISSUE_LIST_API = "https://api.github.com/repos/flutter/flutter/issues"


    fun createIssueListRepository(): IssueListRepository = IssueListRepositoryImpl(
        issueListService = createIssueServiceFacade(),
        queryService = createIssueQueryServiceFacade()
    )


    private fun createIssueServiceFacade() = IssueServiceFacadeImpl(
        apiClient = NetworkFactory.createAPIServiceClient(),
        jsonParser = NetworkFactory.createJsonParser(),
        url = ISSUE_LIST_API
    )

    private fun createIssueQueryServiceFacade(): IssueQueryServiceFacade =
        IssueQueryServiceFacadeImpl(
            apiClient = NetworkFactory.createAPIServiceClient(),
            jsonParser = NetworkFactory.createJsonParser()
        )

    /**
     * Builds a URL for querying issues based on the provided query text and query type.
     *
     * @param queryText The text to query issues by.
     * @param type The [QueryType] defining how to filter the issues (e.g., by title).
     * @return The full URL string to be used for querying issues.
     */
    fun buildIssueSearchURL(queryText: String, type: QueryType): String {
        return when (type) {
            QueryType.Title -> "https://api.github.com/search/issues?q=$queryText+in:title+repo:flutter/flutter"
            else -> "https://api.github.com/search/issues?q=xyz+repo:flutter/flutter"
        }
    }

}