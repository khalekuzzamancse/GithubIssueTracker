@file:Suppress("FunctionName")
package issue_details.data.repository

import issue_details.data.entity.CommentEntity
import issue_details.data.factory.Factory
import issue_details.data.utils.EntityToModel
import issue_details.domain.model.CommentModel
import issue_details.domain.model.IssueDetailsModel
import issue_details.domain.repository.IssueDetailsRepository

/**
 * - Implementation of [IssueDetailsRepository]
 * - To avoid tight coupling UI layer should not use it directly,
 * instead UI layer should use it via di_container factory
 */
@Suppress("Unused")
class IssueDetailsRepositoryImpl : IssueDetailsRepository {
    override suspend fun fetchDetails(issueNumber: String): Result<IssueDetailsModel> {
        val result = Factory.createIssueDetailsServiceFacade().requestDetails(issueNumber)
        return result.fold(
            onSuccess = { entity ->
                Result.success(EntityToModel().toModel(entity))
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }

    override suspend fun fetchComments(issueNumber: String): Result<List<CommentModel>> {
        val result = Factory.createIssueDetailsServiceFacade().requestComments(issueNumber)
        return result.fold(
            onSuccess = { entity ->
                Result.success(entity._toCommentModel())
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )

    }

    /** convert entity to model*/
    private fun List<CommentEntity>._toCommentModel(): List<CommentModel> {
        return this.map { EntityToModel().toModel(it) }
    }


}