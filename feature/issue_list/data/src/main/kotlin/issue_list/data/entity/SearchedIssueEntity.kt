package issue_list.data.entity

import kotlinx.serialization.Serializable

@Serializable
internal data class SearchedIssueEntity(
    val items:List<IssueEntity>
)