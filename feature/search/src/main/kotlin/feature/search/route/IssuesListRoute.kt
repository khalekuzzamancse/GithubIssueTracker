package feature.search.route

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.issue_list.components.IssueListViewController
import feature.issue_list.components.IssuesListView
import issue_list.domain.repository.QueryType


@Composable
fun IssuesListSearchRoute(
    modifier: Modifier = Modifier,
    controller: IssueListViewController,
    onDetailsRequest: (id: String) -> Unit,
    onUserProfileRequest: (userName: String) -> Unit,
) {
    val queryText = "abc"
    LaunchedEffect(Unit) {
        //start fetching...
        controller.searchIssues(queryText, QueryType.Title)

    }

    val issues = controller.issues.collectAsState().value
    val showProgressBar = (controller.isLoading.collectAsState().value || issues == null)
    if (showProgressBar) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(64.dp))
        }
    } else {
        if (issues != null) {
            IssuesListView(
                modifier = modifier,
                issues = issues,
                onDetailsRequest = onDetailsRequest,
                onUserProfileRequest = onUserProfileRequest,
                highlightedText = queryText
            )
        }
    }

}
