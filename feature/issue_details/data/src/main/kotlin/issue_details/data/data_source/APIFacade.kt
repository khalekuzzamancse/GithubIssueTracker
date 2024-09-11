package issue_details.data.data_source

import core.network.GetRequests
import issue_details.data.entity.IssueDetailsEntity

/** Should not access from outer module*/
@Suppress("Unused")
internal class APIFacade {
    suspend fun requestDetails(
        issueNo: String,
    ): Result<IssueDetailsEntity> {
        return GetRequests()
            .request<IssueDetailsEntity>(
                url = "https://api.github.com/repos/flutter/flutter/issues/$issueNo"
            )

    }

}
