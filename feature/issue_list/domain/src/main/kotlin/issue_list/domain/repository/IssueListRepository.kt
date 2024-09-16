package issue_list.domain.repository

import issue_list.domain.model.IssueModel

/**
 * - This abstraction will be used for loose coupling. The data module should implement it,
 * and the DI container module should provide the dependency through a factory.
 */
@Suppress("Unused")
interface IssueListRepository {
    suspend fun fetchIssues(): Result<List<IssueModel>>
    /**Used for search issue*/
    suspend fun queryIssues(queryText:String, type:QueryType, ignoreKeyword:String): Result<List<IssueModel>>
}
enum class QueryType{
    Title,All
}