package issue_list.data.data_source

import core.network.GetRequests
import issue_list.data.entity.IssueEntity

/** Should not access from outer module*/
internal class APIFacade {
    suspend fun requestIssueList() = GetRequests()
        .request<List<IssueEntity>>(
            url = " https://api.github.com/repos/flutter/flutter/issues"
        )

}
