@file:Suppress("FunctionName")

package issue_details.data.factory

import android.util.Log
import core.network.component.ApiServiceClient
import core.network.component.JsonParser
import issue_details.data.data_source.IssueDetailsServiceFacade
import issue_details.data.entity.CommentEntity
import issue_details.data.entity.IssueDetailsEntity
import kotlinx.serialization.builtins.ListSerializer

/**
 * A facade for retrieving issue lists from an API.
 *
 * - This class acts as a facade that hides the underlying complexity of interacting with the API and parsing the JSON response.
 * - It uses [ApiServiceClient] to make the API request and [JsonParser] to parse the JSON response [IssueDetailsEntity] object.
 * - Clients can interact with this class without needing to know how the API calls are made or how the JSON parsing works.
 *
 * @property apiClient The [ApiServiceClient] used to make API requests.
 * @property jsonParser The [JsonParser] used to parse JSON responses.
 */
internal class IssueDetailsServiceFacadeImpl(
    private val apiClient: ApiServiceClient,
    private val jsonParser: JsonParser
) : IssueDetailsServiceFacade {
    override suspend fun requestDetails(issueNo: String): Result<IssueDetailsEntity> {
        val url = IssueDataFactory.createDetailsURL(issueNo)
        val jsonResult = apiClient.retrieveJsonData(url)
        return jsonResult.fold(
            onSuccess = { json ->
                val entityResult = jsonParser.parse(json, IssueDetailsEntity.serializer())
                entityResult//Exception Related to parsing will propagate up
            },
            onFailure = { exception ->
                //Exception related to retrieving
                Result.failure(exception._createError())
            }
        )
    }

    override suspend fun requestComments(issueNo: String): Result<List<CommentEntity>> {
        val url = IssueDataFactory.createCommentURL(issueNo)
        val jsonResult = apiClient.retrieveJsonData(url)
        Log.d(this.javaClass.simpleName,"$jsonResult")
        return jsonResult.fold(
            onSuccess = { json ->
                val entityResult =
                    jsonParser.parse(json, ListSerializer(CommentEntity.serializer()))
                entityResult
            },
            onFailure = { exception ->
                Result.failure(exception._createError())
            }
        )

    }

    private fun Throwable._createError() = Throwable(
        message = this.message ?: "Unable to retrieve issue list",
        cause = this.cause
            ?: Throwable("I'm ${this@IssueDetailsServiceFacadeImpl.javaClass.name}  failed at network layer\n${this.printStackTrace()}")
    )

}