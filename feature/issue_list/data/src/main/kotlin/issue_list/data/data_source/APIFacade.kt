package issue_list.data.data_source

import core.network.GetRequests
import issue_list.data.entity.IssueEntity
import issue_list.data.entity.SearchedIssueEntity
import issue_list.domain.repository.QueryType

/** Should not access from outer module*/
internal class APIFacade {

    suspend fun requestIssueList() = GetRequests()
        .request<List<IssueEntity>>(
            url = "https://api.github.com/repos/flutter/flutter/issues"
        )

    /**For searching*/
    suspend fun requestIssueList(queryText: String, type: QueryType): Result<SearchedIssueEntity> {
        val url = when (type) {
            QueryType.Title -> "https://api.github.com/search/issues?q=$queryText+in:title+repo:flutter/flutter"
            else -> "https://api.github.com/search/issues?q=xyz+repo:flutter/flutter"
        }
       return GetRequests().request<SearchedIssueEntity>(url = url)
    }

}
