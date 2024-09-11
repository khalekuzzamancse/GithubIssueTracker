package issue_details.data.data_source

import core.network.GetRequests
import issue_details.data.entity.CommentEntity
import issue_details.data.entity.IssueDetailsEntity

/** Should not access from outer module*/
@Suppress("Unused")
internal class APIFacade {
    private val baseUrl = "https://api.github.com/repos/flutter/flutter"
    suspend fun requestDetails(issueNo: String): Result<IssueDetailsEntity> {
        return GetRequests()
            .request<IssueDetailsEntity>(
                url = "$baseUrl/issues/154741"
            )

    }

    suspend fun requestComments(issueNo: String, ): Result<List<CommentEntity>> {
        return GetRequests()
            .request<List<CommentEntity>>(
                url = "https://api.github.com/repos/flutter/flutter/issues/154741/comments"
            )
    }


}
