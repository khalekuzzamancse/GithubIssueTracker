package issue_list.data.repository

import issue_list.data.data_source.APIFacade
import issue_list.data.entity.IssueEntity
import issue_list.data.utils.EntityToModel
import issue_list.domain.model.IssueModel
import issue_list.domain.repository.IssueListRepository

/**
 * - Implementation of [IssueListRepository]
 * - To avoid tight coupling UI layer should not use it directly,
 * instead UI layer should use it via di_container factory
 */
@Suppress("Unused")
class IssueListRepositoryImpl : IssueListRepository {
    override suspend fun fetchIssues(): Result<List<IssueModel>> {
        val result = APIFacade().requestIssueList()
        return if (result.isSuccess) {
            toModel(result)
        } else
            Result.failure(createFailureException(result.exceptionOrNull()))
    }


    /** convert entity to model*/
    private fun toModel(result: Result<List<IssueEntity>>): Result<List<IssueModel>> {
        return try {
            Result.success(result
                .getOrThrow()
                .map { entity -> EntityToModel().toEntity(entity) })

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