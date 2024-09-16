@file:Suppress("FunctionName")

package issue_list.data.repository

import issue_list.data.data_source.IssueQueryServiceFacade
import issue_list.data.data_source.IssueServiceFacade
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
 * - Client module should not crate direct `instance` of it but can use it so
 *  * return the  `instance` via `factory method`
 */
class IssueListRepositoryImpl internal constructor(
    private val issueListService: IssueServiceFacade,
    private val queryService: IssueQueryServiceFacade,
) : IssueListRepository {
    override suspend fun fetchIssues(): Result<List<IssueModel>> {
        val result = issueListService.retrieveIssueList()
        return result.fold(
            onSuccess = { entities ->

                Result.success(entities._toModel())
            },
            onFailure = { exception ->
                Result.failure(exception)

            }
        )
    }

    /**
     * @param queryText the keyword that will be searched
     * @param type such as searched in title,description, etc. right now only searching in the title
     * @param ignoreKeyword based on this keyword issue will be filtered, right now, not found any github api
     * to search using ignore keyword, that is why manually filtering the issue, currently ignoring keyword only from the title,
     * if need to ignore from other property such as label, description, comment then modify it
     */

    override suspend fun queryIssues(
        queryText: String,
        type: QueryType,
        ignoreKeyword: String
    ): Result<List<IssueModel>> {
        val result = queryService.queryIssues(queryText, type, ignoreKeyword)
        return result.fold(
            onSuccess = { entity ->
                Result.success(entity._searchedEntityToModel(ignoreKeyword))
            },
            onFailure = { exception ->
                Result.failure(exception)

            }
        )
    }

    /** convert entity to model*/
    private fun List<IssueEntity>._toModel(): List<IssueModel> {
        return this.map { entity -> EntityToModel().toEntity(entity) }

    }

    /** - Convert entity to model
     * - Right now, not found any github api to search using ignore keyword, that is why manually filtering the issues
     * - Right now it ignore keyword only from the title, if need to ignore from other property
     * such as label, description, comment then modify it
     * */
    private fun SearchedIssueEntity._searchedEntityToModel(
        ignoreKeyword: String
    ): List<IssueModel> {
        var entities = this
            .items
            .map { EntityToModel().toEntity(it) }
        if (ignoreKeyword.isNotEmpty())
        //TODO: ignore the other property  also if needed
            entities = entities.filter { !it.title.contains(ignoreKeyword) }
        return entities
    }


}