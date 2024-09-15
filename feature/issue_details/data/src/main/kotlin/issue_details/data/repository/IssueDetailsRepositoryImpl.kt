package issue_details.data.repository

import issue_details.data.data_source.APIFacade
import issue_details.data.entity.CommentEntity
import issue_details.data.entity.IssueDetailsEntity
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
        val result = APIFacade().requestDetails(issueNumber)
        return if (result.isSuccess) {
            toDetailsModel(result)
        } else
            Result.failure(createFailureException(result.exceptionOrNull()))
    }

    override suspend fun fetchComments(issueNumber: String): Result<List<CommentModel>> {
        val result = APIFacade().requestComments(issueNumber)
        return if (result.isSuccess) {
            toCommentModel(result)
        } else
            Result.failure(createFailureException(result.exceptionOrNull()))
    }


    /** convert entity to model*/
    private fun toDetailsModel(result: Result<IssueDetailsEntity>): Result<IssueDetailsModel> {
        return try {
            Result.success(EntityToModel().toModel(result.getOrThrow()))
        } catch (ex: Exception) {
            Result.failure(createFailureException(ex))
        }
    }

    /** convert entity to model*/
    private fun toCommentModel(result: Result<List<CommentEntity>>): Result<List<CommentModel>> {
        return try {
            Result.success(
                result.getOrThrow().map { EntityToModel().toModel(it) }
            )
        } catch (ex: Exception) {
            Result.failure(createFailureException(ex))
        }
    }


    /** Create  meaning error message on exception rise*/
    private fun createFailureException(exception: Throwable?): Throwable {
        val reason:String = exception?.cause?.stackTraceToString() ?:"Unknown reason at ${this.javaClass.name}"
        return Throwable(
            message = "Failed to fetch",
            cause = Throwable(reason))
    }
}