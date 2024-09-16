package issue_list.data.factory

import core.network.component.ApiServiceClient
import core.network.component.JsonParser
import issue_list.data.data_source.IssueServiceFacade
import issue_list.data.entity.IssueEntity
import kotlinx.serialization.builtins.ListSerializer

/**
 * A facade for retrieving issue lists from an API.
 *
 * - This class acts as a facade that hides the underlying complexity of interacting with the API and parsing the JSON response.
 * - It uses [ApiServiceClient] to make the API request and [JsonParser] to parse the JSON response into a list of [IssueEntity] objects.
 * - Clients can interact with this class without needing to know how the API calls are made or how the JSON parsing works.
 *
 * @property apiClient The [ApiServiceClient] used to make API requests.
 * @property jsonParser The [JsonParser] used to parse JSON responses.
 */
internal class IssueServiceFacadeImpl(
    private val apiClient: ApiServiceClient,
    private val jsonParser: JsonParser
) : IssueServiceFacade {

    override suspend fun retrieveIssueList(): Result<List<IssueEntity>> {
        val url = Factory.ISSUE_LIST_API
        val apiResult = apiClient.retrieveJsonData(url)

        return apiResult.fold(
            onSuccess = { json ->
                val issueListResult = jsonParser.parse(json, ListSerializer(IssueEntity.serializer()))
                issueListResult
            },
            onFailure = { ex ->
                Result.failure(
                    Throwable(
                        message = "Unable to retrieve issue list at:${this.javaClass.name} ",
                        cause = ex
                    )
                )
            }
        )
    }
}
