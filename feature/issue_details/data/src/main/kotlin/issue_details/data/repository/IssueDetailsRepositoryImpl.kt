package issue_details.data.repository

import issue_details.data.data_source.APIFacade
import issue_details.data.entity.IssueDetailsEntity
import issue_details.data.utils.EntityToModel
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
            toModel(result)
        } else
            Result.failure(createFailureException(result.exceptionOrNull()))
    }


    /** convert entity to model*/
    private fun toModel(result: Result<IssueDetailsEntity>): Result<IssueDetailsModel> {
        return try {
            Result.success(EntityToModel().toModel(result.getOrThrow()))

        } catch (ex: Exception) {
            Result.failure(createFailureException(ex))
        }
    }

    /** Create  meaning error message on exception rise*/
    private fun createFailureException(exception: Throwable?): Throwable {
        val reason = if (exception == null) "unknown exception" else "$exception"
        return Throwable("Failed to fetch, due to $reason ; at ${this.javaClass.name}")
    }
}