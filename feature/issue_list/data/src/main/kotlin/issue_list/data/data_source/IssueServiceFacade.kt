package issue_list.data.data_source

import issue_list.data.entity.IssueEntity
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

internal interface IssueServiceFacade {
    /**
     * Retrieves the list of issues from the API.
     *
     * - This method abstracts the underlying complexity of making an API request and parsing the response.
     * - The client doesn't need to worry about how the API is called or how the JSON is parsed.
     *
     * @return A [Result] containing the parsed list of issues or an error in case of failure.
     */
    suspend fun retrieveIssueList(): Result<List<IssueEntity>>
}
