@file:Suppress("UnUsed", "ComposableNaming", "FunctionName")

package feature.issue_list.components

import android.util.Log
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
import androidx.compose.ui.unit.dp
import common.ui.LabelViewData
import issue_list.di_container.DIFactory
import issue_list.domain.model.IssueModel
import issue_list.domain.model.LabelModel
import issue_list.domain.repository.QueryType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


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
            Text("No issue found")
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
                }

            }
        }
    }
}


class IssueListViewController {
    private val _issues = MutableStateFlow<List<IssueViewData>?>(null)
    val issues = _issues.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    /**either error or success message,can be shown using snackBar*/
    private val _screenMessage = MutableStateFlow<String?>(null)

    /**Should be used by Screen/Route component that is not making internal*/
    val screenMessage = _screenMessage.asStateFlow()


    internal suspend fun fetchIssues() {
        _isLoading.update { true }
        val response = DIFactory.createIssueListRepository().fetchIssues()
        if (response.isSuccess) {
            updateState(response)
        } else {

            _updateScreenMessage("Failed to fetch details:${response.exceptionOrNull()}")
        }
        _isLoading.update { false }
    }

    /** public so that outer module can use it to build search feature
     * @param ignoreKeyword the keyword that should ignore
     **/
    suspend fun searchIssues(query: String, type: QueryType,ignoreKeyword:String) {
        _isLoading.update { true }
        val response = DIFactory.createIssueListRepository().fetchIssues(query, type,ignoreKeyword)
        if (response.isSuccess) {
            updateState(response)
        } else {
            Log.d("FetchIssue", "FailedTo:${response.exceptionOrNull()}")
            _updateScreenMessage("Failed to fetch details:${response.exceptionOrNull()}")
        }
        _isLoading.update { false }
    }


    /** taking in wrapping in Result so that exception can handle by this method*/
    private fun updateState(result: Result<List<IssueModel>>) {
        try {
            _issues.update {
                result.getOrThrow().map(::toIssueViewData)
            }

        } catch (e: Exception) {
            _updateScreenMessage("Failed to fetch details:$e")
        }
    }


    //TODO:Helper method section-------------
    private fun toLabelViewData(model: LabelModel) = LabelViewData(
        name = model.name,
        hexCode = model.hexCode,
        description = model.description
    )

    private fun toIssueViewData(model: IssueModel) = IssueViewData(
        title = model.title,
        id = model.id,
        creatorName = model.creatorName,
        creatorAvatarLink = model.userAvatarLink,
        createdTime = model.createdTime,
        labels = model.labels.map(::toLabelViewData)
    )

    private fun _updateScreenMessage(msg: String?) {
        CoroutineScope(Dispatchers.Default).launch {
            _screenMessage.update { msg }
            delay(3000)
            _screenMessage.update { null }//clear message after 3 sec
        }

    }
}
