@file:Suppress("ComposableNaming")

package feature.search.route

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.ui.VerticalSpace_8Dp
import feature.issue_list.components.IssueListViewController
import feature.issue_list.components.IssuesListView
import issue_list.domain.repository.QueryType


@Composable
fun IssuesSearchRoute(
    modifier: Modifier = Modifier,
    controller: IssueListViewController,
    onDetailsRequest: (id: String) -> Unit,
    onUserProfileRequest: (userName: String) -> Unit,
) {
    /**Small state it's fine to preserve here instead of hoisting to reduce burden of the parent function or ViewModel.*/
    var queryText by rememberSaveable { mutableStateOf("") }
    /**Small state it's fine to preserve here instead of hoisting to reduce burden of the parent function or ViewModel.*/
    var ignoreKeyword by rememberSaveable { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth()) {
        SearchView(
            modifier = Modifier.fillMaxWidth(),
            query = queryText,
            onIgnoreKeywordChanged = {
                ignoreKeyword=it
            },
            onQueryChanged = {
                queryText = it
            },
            onSearch = {
                queryText = it

            },
            ignoreKeyword = ignoreKeyword

        )
        VerticalSpace_8Dp()
        _IssuesListSearchRoute(
            modifier = modifier,
            controller = controller,
            query = queryText,
            onDetailsRequest = onDetailsRequest,
            ignoreKeyword =ignoreKeyword ,
            onUserProfileRequest = onUserProfileRequest
        )
    }

}

@Composable
private fun _IssuesListSearchRoute(
    modifier: Modifier = Modifier,
    controller: IssueListViewController,
    query: String,
    ignoreKeyword: String,
    onDetailsRequest: (id: String) -> Unit,
    onUserProfileRequest: (userName: String) -> Unit,
) {
    //search on each time query text changed
    LaunchedEffect(query,ignoreKeyword) {
        //start fetching...
        controller.searchIssues(
            query = query,
            type = QueryType.Title,
            ignoreKeyword = ignoreKeyword
        )
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
                highlightedText = query
            )
        }
    }

}
