package feature_lissuelist.issue_list.route

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.ui.SnackBar
import feature_lissuelist.factory.IssueListFactory
import feature_lissuelist.issue_list.components.IssuesListView

@Composable
fun IssuesListRoute(
    modifier: Modifier = Modifier,
    onDetailsRequest: (issueNum: String) -> Unit,
    onUserProfileRequest: (userName: String) -> Unit,
) {
    val viewModel = IssueListFactory.createIssueListController()
    LaunchedEffect(Unit) {
        //start fetching...
        viewModel.onIssueListRequest()
    }

    val issues = viewModel.issues.collectAsState().value
    val showProgressBar = (viewModel.isLoading.collectAsState().value || issues == null)
    Scaffold(
        snackbarHost = {
            viewModel.screenMessage.collectAsState().value?.let {
                SnackBar(
                    message = it,
                    onDismissRequest = {
                        viewModel.onScreenMessageDismissRequest()
                    }
                )
            }

        }
    ) { innerPadding ->
        if (showProgressBar) {
            Box(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(64.dp))
            }
        } else {
            if (issues != null) {
                IssuesListView(
                    modifier = modifier.padding(innerPadding),
                    issues = issues,
                    onDetailsRequest = onDetailsRequest,
                    onUserProfileRequest = onUserProfileRequest,
                    highlightedText = null//because this module not provide the search feature
                )
            }
        }
    }


}
