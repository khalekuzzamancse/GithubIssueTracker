@file:Suppress("UnUsed")
package issue_details.data.data_source

import issue_details.data.entity.CommentEntity
import issue_details.data.entity.IssueDetailsEntity

/**
 * Implements the `Facade design pattern` to simplify and abstract the complexity of fetching data from an API and parsing the response.
 *
 * - This class serves as a facade, providing a simplified interface for clients to retrieve data
 *   without needing to know the internal details of how the API request is made or how the JSON response is parsed.
 * - The underlying complexity of network communication (using [ApiServiceClient]) and JSON parsing (using [JsonParser])
 *   is encapsulated within this class, allowing clients to interact with it in a straightforward manner.
 * - This pattern promotes a clean separation of concerns and makes it easier to change the implementation of the API client or parser
 *   without affecting the client code that consumes this class.
 * - Ideal for cases where clients need to retrieve data but should remain decoupled from low-level networking and parsing logic.
 */
internal interface IssueDetailsServiceFacade {
    /**
     * Requests the details of a specific issue by issue number.
     *
     * @param issueNo The issue number to retrieve details for.
     * @return A [Result] containing the [IssueDetailsEntity] or an error if the request fails.
     */
    suspend fun requestDetails(issueNo: String): Result<IssueDetailsEntity>

    /**
     * Requests the list of comments for a specific issue by issue number.
     *
     * @param issueNo The issue number to retrieve comments for.
     * @return A [Result] containing the list of [CommentEntity] objects or an error if the request fails.
     */
    suspend fun requestComments(issueNo: String): Result<List<CommentEntity>>
}
