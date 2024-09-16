@file:Suppress("FunctionName")
package issue_list.data.factory

import core.network.component.ApiServiceClient
import core.network.component.JsonParser
import issue_list.data.data_source.IssueQueryServiceFacade
import issue_list.data.entity.IssueEntity
import issue_list.data.entity.SearchedIssueEntity
import issue_list.domain.repository.QueryType

/**
 * A facade for retrieving issue lists from an API.
 *
 * - This class acts as a facade that hides the underlying complexity of interacting with the API and parsing the JSON response.
 * - It uses [ApiServiceClient] to make the API request and [JsonParser] to parse the JSON response into a list of [IssueEntity] objects.
 * - Clients can interact with this class without needing to know how the API calls are made or how the JSON parsing works.

 * - Caution:
 * - Right now, not found any github api to search using ignore keyword, that is why manually filtering the issues
 * - Right now it ignore keyword only from the title, if need to ignore from other property
 *  such as label, description, comment then modify it

 *  @property apiClient The [ApiServiceClient] used to make API requests.
 * @property jsonParser The [JsonParser] used to parse JSON responses.
 */
internal class IssueQueryServiceFacadeImpl(
    private val apiClient: ApiServiceClient,
    private val jsonParser: JsonParser
) : IssueQueryServiceFacade {

    override suspend fun queryIssues(queryText: String, type: QueryType, ignoreKeyword: String): Result<SearchedIssueEntity> {
        val result = apiClient.retrieveJsonData(IssueListDataFactory.buildIssueSearchURL(queryText, type))

        return result.fold(
            onSuccess = { json ->
                val searchResult = jsonParser.parse(json, SearchedIssueEntity.serializer())
                searchResult//Exception Related to parsing will propagate up
            },
            onFailure = { ex ->
                //Exception related to retrieving
                Result.failure(ex._createError())
            }
        )
    }

    private fun Throwable._createError() = Throwable(
        message = this.message ?: "Unable to retrieve issue list",
        cause = this.cause
            ?: Throwable("I'm ${this@IssueQueryServiceFacadeImpl.javaClass.name}  failed at network layer\n${this.printStackTrace()}")
    )
}
