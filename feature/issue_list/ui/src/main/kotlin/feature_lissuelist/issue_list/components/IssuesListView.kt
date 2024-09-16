@file:Suppress("UnUsed", "ComposableNaming", "FunctionName")

package feature_lissuelist.issue_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import common.ui.HorizontalGap_8Dp
import common.ui.SnackBarMessage
import common.ui.VerticalSpace_8Dp
import feature_lissuelist.R
import issue_list.domain.repository.QueryType
import kotlinx.coroutines.flow.StateFlow


/**
 * - Show list of issue
 * - Should call when issue list is ready because it does not handle data fetching or loading ,
 * so it does not have any progress bar
 * - It show helpful UI when the issue list is empty
 *  @param highlightedText searched/query text that will be highlighted while using search feature
 */
@Composable
fun IssuesListView(
    modifier: Modifier = Modifier,
    issues: List<IssueViewData>,
    highlightedText: String?,
    onDetailsRequest: (id: String) -> Unit,
    onUserProfileRequest: (userName: String) -> Unit,
) {

    if (issues.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.no_issue_found))
        }
    } else {
        LazyColumn(modifier) {
            itemsIndexed(issues) { index, issue ->
                val isNotLastItem = (index != issues.lastIndex)
                IssueView(
                    modifier = Modifier.padding(8.dp),
                    info = issue,
                    highlightedText = highlightedText,
                    onDetailsRequest = { onDetailsRequest(issue.id) },
                    onUserProfileRequest = { onUserProfileRequest(issue.creatorName) }
                )
                if (isNotLastItem) {
                    HorizontalDivider()
                    VerticalSpace_8Dp()
                }


            }
        }
    }
}

/**
 * - It is controller for [IssuesListView]
 * - It manage the state and response of event of the [IssuesListView] UI
 * -Depending on abstraction instead of concretion
 *  - It will reduce the responsibility of ViewModel or the route controller
 *  - Delegate to it to avoid monster ViewModel/RouteController
 *  - Outer module should not use it own purpose that is why some filed are made internal
 */

interface  IssueListViewController {
    /**
     * - The observable details ,nullable because may be failed to fetch-
     * - public  because other feature module such as feature:search can be use it*/
    val issues: StateFlow<List<IssueViewData>?>
    val isLoading: StateFlow<Boolean>

    /**- either error or success message,can be shown using snackBar
     * - public  because other feature module such as feature:search can be use it*/
    val screenMessage: StateFlow<SnackBarMessage?>

    /** - public  because other feature module such as feature:search can be use it
     * @param ignoreKeyword the keyword that should ignore
     **/
    suspend fun onIssueSearch(query: String, type: QueryType, ignoreKeyword: String)

    /**SnackBar dismiss request*/
    fun onScreenMessageDismissRequest()
}

