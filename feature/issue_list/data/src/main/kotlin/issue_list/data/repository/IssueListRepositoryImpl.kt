package issue_list.data.repository

import issue_list.data.data_source.APIFacade
import issue_list.data.entity.IssueEntity
import issue_list.data.entity.SearchedIssueEntity
import issue_list.data.utils.EntityToModel
import issue_list.domain.model.IssueModel
import issue_list.domain.repository.IssueListRepository
import issue_list.domain.repository.QueryType

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

    /**
     * @param queryText the keyword that will be searched
     * @param type such as searched in title,description, etc. right now only searching in the title
     * @param ignoreKeyword based on this keyword issue will be filtered, right now, not found any github api
     * to search using ignore keyword, that is why manually filtering the issue, currently ignoring keyword only from the title,
     * if need to ignore from other property such as label, description, comment then modify it
     */

    override suspend fun fetchIssues(
        queryText: String,
        type: QueryType,
        ignoreKeyword: String
    ): Result<List<IssueModel>> {
        val result = APIFacade().requestIssueList(queryText, type)
        return if (result.isSuccess) {
            searchedEntityToModel(result, ignoreKeyword)
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

    /** - Convert entity to model
     * - Right now, not found any github api to search using ignore keyword, that is why manually filtering the issues
     * - Right now it ignore keyword only from the title, if need to ignore from other property
     * such as label, description, comment then modify it
     * */
    private fun searchedEntityToModel(
        result: Result<SearchedIssueEntity>,
        ignoreKeyword: String
    ): Result<List<IssueModel>> {
        return try {
            var entities = result
                .getOrThrow()
                .items
                .map { entity -> EntityToModel().toEntity(entity) }
            if (ignoreKeyword.isNotEmpty())
            //TODO: ignore the other property  also if needed
                entities = entities.filter { entity -> !entity.title.contains(ignoreKeyword) }
            Result.success(entities)
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