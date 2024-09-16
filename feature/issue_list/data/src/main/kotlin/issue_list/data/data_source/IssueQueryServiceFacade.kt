package issue_list.data.data_source

import issue_list.data.entity.IssueEntity
import issue_list.data.entity.SearchedIssueEntity
import issue_list.domain.model.IssueModel
import issue_list.domain.repository.QueryType

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
internal interface IssueQueryServiceFacade {
    /**
     * Queries issues based on the provided query text, type, and keyword to ignore.
     *
     * @param queryText The text to query issues by.
     * @param type The [QueryType] defining how to filter the issues.
     * @param ignoreKeyword A keyword to ignore when filtering issues.
     * @return A [Result] containing the filtered list of [IssueEntity] objects or an error if the request fails.
     */
    suspend fun queryIssues(queryText: String, type: QueryType, ignoreKeyword: String): Result<SearchedIssueEntity>
}
