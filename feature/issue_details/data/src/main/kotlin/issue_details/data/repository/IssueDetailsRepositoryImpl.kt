@file:Suppress("FunctionName")

package issue_details.data.repository

import issue_details.data.data_source.IssueDetailsServiceFacade
import issue_details.data.entity.CommentEntity
import issue_details.data.utils.EntityToModel
import issue_details.domain.model.CommentModel
import issue_details.domain.model.IssueDetailsModel
import issue_details.domain.repository.IssueDetailsRepository

/**
 * - Implementation of [IssueDetailsRepository]
 * - To avoid tight coupling UI layer should not use it directly,
 * instead UI layer should use it via di_container factory
 * - Client module should not crate direct `instance` of it but can use it so
 * return the  `instance` via `factory method`
 */
@Suppress("Unused")
class IssueDetailsRepositoryImpl internal constructor(
    private val service: IssueDetailsServiceFacade
) : IssueDetailsRepository {
    override suspend fun fetchDetails(issueNumber: String): Result<IssueDetailsModel> {
        val result = service.requestDetails(issueNumber)
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
        val result = service.requestComments(issueNumber)
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