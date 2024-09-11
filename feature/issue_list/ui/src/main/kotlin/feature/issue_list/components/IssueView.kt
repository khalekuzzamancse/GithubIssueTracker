@file:Suppress("UnUsed", "ComposableNaming", "FunctionName")

package feature.issue_list.components
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.ui.LabelListView
import common.ui.LabelViewData
import common.ui.UserShortInfoView
import issue_list.di_container.DIFactory
import issue_list.domain.model.IssueModel
import issue_list.domain.model.LabelModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * - Show list of issue
 * - Should call when issue list is ready because it does not handle data fetching or loading ,
 * so it does not have any progress bar
 */
@Composable
internal fun IssuesList(
    modifier: Modifier = Modifier,
    issues: List<IssueViewData>,
    onDetailsRequest: (id: String) -> Unit,
    onUserProfileRequest: (userName: String) -> Unit,
    ) {
    LazyColumn(modifier) {
        itemsIndexed(issues) { index, issue ->
            val isNotLastItem = (index != issues.lastIndex)
            _IssueView(
                modifier = Modifier.padding(8.dp),
                info = issue,
                onDetailsRequest = { onDetailsRequest(issue.id) },
                onUserProfileRequest = { onUserProfileRequest(issue.creatorName) }
            )
            if (isNotLastItem) {
                HorizontalDivider()
            }

        }
    }
}


class IssueListViewController {
    private val _issues = MutableStateFlow<List<IssueViewData>?>(null)
    internal val issues = _issues.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    /**either error or success message,can be shown using snackBar*/
    private val _screenMessage = MutableStateFlow<String?>(null)

    /**Should be used by Screen/Route component that is not making internal*/
    val screenMessage = _screenMessage.asStateFlow()


    internal suspend fun fetchIssues() {
        val response = DIFactory.createIssueListRepository().fetchIssues()
        if (response.isSuccess) {
            updateState(response)
        } else {
            _updateScreenMessage("Failed to fetch details:${response.exceptionOrNull()}")
        }
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
        labels =model.labels.map(::toLabelViewData)
    )

    private fun _updateScreenMessage(msg: String?) {
        CoroutineScope(Dispatchers.Default).launch {
            _screenMessage.update { msg }
            delay(3000)
            _screenMessage.update { null }//clear message after 3 sec
        }

    }
}


@SuppressLint("ComposableNaming")
@Composable
private fun _IssueView(
    modifier: Modifier = Modifier,
    info: IssueViewData,
    onDetailsRequest: () -> Unit,
    onUserProfileRequest: () -> Unit,
) {
    val labelColor = MaterialTheme.typography.labelMedium.color
    val labelStyle = MaterialTheme.typography.labelMedium.copy(
        color = labelColor.copy(alpha = 0.6f)
    )
    _IssueViewLayout(
        modifier = modifier,
        title = {
            Text(
                modifier = it.clickable { onDetailsRequest() },
                text = info.title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        timestamp = {
            Text(
                modifier = it,
                text = _extractDateFromTimestamp(info.createdTime),
                style = labelStyle
            )
        },
        creatorInfo = {
            UserShortInfoView(
                username = info.creatorName,
                avatarLink = info.creatorAvatarLink,
                onUsernameOrImageClick = onUserProfileRequest
            )
        },
        labels = if (info.labels.isNotEmpty()) {
            @Composable {
                LabelListView(//from common.ui module
                    labels = info.labels.map {
                        LabelViewData(
                            name = it.name,
                            hexCode = it.hexCode,
                            description = it.description
                        )
                    })
            }
        } else null
    )

}

@SuppressLint("NewApi")
private fun _extractDateFromTimestamp(timestamp: String): String {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val dateTime = LocalDateTime.parse(timestamp, formatter)
    return dateTime.toLocalDate().toString()
}


/**
 * @param creatorName is github user name of the issue creator
 */
data class IssueViewData(
    val id: String,
    val title: String,
    val createdTime: String,
    val creatorAvatarLink: String,
    val creatorName: String,
    val labels: List<LabelViewData>
)



/**
 * - This composable is only responsible for layout the component without worrying about look and feel of
 * component, this ensure single responsibility
 * - Following Strategy Design pattern so that using slot
 * @param title is the title  of the issue
 * @param timestamp when the issue opened
 * @param labels label list may be empty that is why making nullable to avoid unnecessary margin/padding
 */
@SuppressLint("ComposableNaming")
@Composable
private fun _IssueViewLayout(
    modifier: Modifier = Modifier,
    title: @Composable (Modifier) -> Unit,
    timestamp: @Composable (Modifier) -> Unit,
    creatorInfo: @Composable (Modifier) -> Unit,
    labels: (@Composable (Modifier) -> Unit)?,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            //   verticalAlignment = Alignment.CenterVertically
        ) {
            // Title takes most of the space, wraps onto the next line if necessary
            title(
                Modifier
                    .weight(1f)
                    .padding(end = 2.dp)
            )
            // Timestamp stays at the end
            timestamp(Modifier.wrapContentWidth(Alignment.End))
        }
        creatorInfo(Modifier)
        if (labels != null) {
            Spacer(Modifier.height(4.dp))
            labels(Modifier)
        }

    }

}