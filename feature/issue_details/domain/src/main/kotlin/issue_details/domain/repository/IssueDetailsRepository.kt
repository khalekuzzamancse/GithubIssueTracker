package issue_details.domain.repository

import issue_details.domain.model.CommentModel
import issue_details.domain.model.IssueDetailsModel

/**
 * - This abstraction will be used for loose coupling. The data module should implement it,
 * and the DI container module should provide the dependency through a factory.
 */
@Suppress("Unused")
interface IssueDetailsRepository {
    suspend fun fetchDetails(issueNumber:String): Result<IssueDetailsModel>
    suspend fun fetchComments(issueNumber:String): Result<List<CommentModel>>
}