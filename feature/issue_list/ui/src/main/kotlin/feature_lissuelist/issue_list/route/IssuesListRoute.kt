@file:Suppress("ComposableNaming")
package feature_lissuelist.issue_list.route

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import common.ui.SimpleTopBar
import common.ui.SnackBarMessage
import common.ui.TextWithLessOpacity
import feature_lissuelist.R
import feature_lissuelist.factory.IssueListFactory
import feature_lissuelist.issue_list.components.IssuesListView

@Composable
fun IssuesListRoute(
    modifier: Modifier = Modifier,
    onDetailsRequest: (issueNum: String) -> Unit,
    onUserProfileRequest: (userName: String) -> Unit,
    onScreenMessageUpdate:(SnackBarMessage)->Unit,
) {
    val viewmodel = IssueListFactory.createIssueListController()
    val screenMessage=viewmodel.screenMessage.collectAsState().value
    LaunchedEffect(screenMessage) {
        if (screenMessage!=null)
            onScreenMessageUpdate(screenMessage)
    }

    val issues = viewmodel.issues.collectAsState().value
    val showProgressBar = (viewmodel.isLoading.collectAsState().value || issues == null)

        if (showProgressBar) {
            Box(
                Modifier
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(64.dp))
            }
        } else {
            if (issues != null) {
                Scaffold (
                    modifier = modifier,
                    topBar = {
                        _TopBar()
                    }
                ){innerPadding->
                    IssuesListView(
                        modifier = Modifier.padding(innerPadding),
                        issues = issues,
                        onDetailsRequest = onDetailsRequest,
                        onUserProfileRequest = onUserProfileRequest,
                        highlightedText = null//because this module not provide the search feature
                    )
                }

            }

    }


}

@Composable
private fun _TopBar(modifier: Modifier = Modifier) {
    SimpleTopBar(
        modifier = modifier,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.flutter_issues),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.width(4.dp))
                TextWithLessOpacity(text = "(master)")

            }
        }
    )

}