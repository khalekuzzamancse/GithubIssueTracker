@file:Suppress("UnUsed", "ComposableNaming", "FunctionName")

package feature_issuedetails.details.route

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import common.ui.HorizontalGap_8Dp
import common.ui.LabelViewData
import common.ui.SimpleTopBar
import common.ui.SnackBarMessage
import common.ui.TextWithLessOpacity
import common.ui.UserShortInfoView
import common.ui.VerticalGap_32Dp
import common.ui.VerticalSpace_8Dp
import feature_issuedetails.R
import feature_issuedetails.details.components.AssigneeListView
import feature_issuedetails.details.components.AssigneeViewData
import feature_issuedetails.details.components.CommentListView
import feature_issuedetails.details.components.CommentViewData
import feature_issuedetails.details.components.Description
import feature_issuedetails.details.components.LabelList
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

@Composable
fun IssueDetailsRoute(
    modifier: Modifier = Modifier,
    issueNum: String,
    onUserProfileRequest: (username: String) -> Unit,
    onScreenMessageUpdate:(SnackBarMessage)->Unit,
) {
    val viewmodel = remember { IssueDetailsViewModel() }
    val screenMessage=viewmodel.screenMessage.collectAsState().value
    LaunchedEffect(screenMessage) {
        if (screenMessage!=null)
            onScreenMessageUpdate(screenMessage)
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(
                modifier=Modifier,
                title = stringResource(R.string.issue_details)
            )
        }
    ) { innerPadding ->
        IssueDetailsView(
            modifier = Modifier.padding(innerPadding).verticalScroll(rememberScrollState()),
            controller = viewmodel,
            issueNum = issueNum,
            onUserProfileRequest = onUserProfileRequest
        )

    }

}



/**
 * - Represent the Issue details
 * - Does not handle the Layout structure, use Delegation design pattern concept to delegation
 * layout to other component
 * - By default not scrollable,pass modifier with scrollable in order to scroll
 */
@Composable
private fun IssueDetailsView(
    modifier: Modifier = Modifier,
    controller: IssueDetailsViewController,
    issueNum: String,
    onUserProfileRequest: (username: String) -> Unit,
) {
    LaunchedEffect(Unit) {
        controller.onDetailsRequest(issueNum)
    }
    controller.details.collectAsState().value?.let { details ->
        _IssueDetailsLayout(
            modifier = modifier,
            title = {
                Text(
                    modifier = it,
                    text = details.title,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            issueNumber = {
                _IssueNumber(number = details.issueNum, modifier = it)
            },
            status = {
                _IssueStatus(modifier = it, status = details.status)
            },
            creatorInfo = {
                UserShortInfoView(//From common:ui module
                    username = details.creatorName,
                    avatarLink = details.creatorAvatarLink,
                    modifier = it,
                    onUsernameOrImageClick = { onUserProfileRequest(details.creatorName) }
                )
            },
            labels = {
                LabelList(
                    modifier = it,
                    labels = details.labels.map { label ->
                        LabelViewData(
                            name = label.name,
                            hexCode = label.hexCode,
                            description = label.description
                        )
                    }
                )
            },
            assignees = {
                AssigneeListView(
                    modifier = it,
                    assignees = details.assignees,
                    onUserProfileRequest = onUserProfileRequest
                )
            },
            description = {
                Description(modifier = it, body = details.description)
            },
            comments = {
                CommentListView(
                    modifier = it,
                    comments = details.comments,
                    onUserProfileRequest = onUserProfileRequest
                )
            }
        )
    }
    if (controller.details.collectAsState().value == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(64.dp))
        }
    }


}

/**
 * - It is controller for [IssueDetailsView]
 * - It manage the state and response of event of the [IssueDetailsView] UI
 * -Depending on abstraction instead of concretion
 *  - It will reduce the responsibility of ViewModel or the route controller
 *  - Delegate to it to avoid monster ViewModel/RouteController
 *  - Outer module should not use it own purpose that is why some filed are made internal
 *

 */
internal interface IssueDetailsViewController {
    /**The observable details ,nullable because may be failed to fetch */
    val details: StateFlow<IssueDetailsViewData?>
    val isLoading: StateFlow<Boolean>

    /**either error or success message,can be shown using snackBar*/
    val screenMessage: StateFlow<SnackBarMessage?>

    /**request for issue details ,following unidirectional data flow so issues details will be notified via [details]*/
    suspend fun onDetailsRequest(issueNumber: String)

    /**SnackBar dismiss request*/
    fun onScreenMessageDismissRequest()
}


/**
 * - Represent the data or state of [IssueDetailsView]
 */
internal data class IssueDetailsViewData(
    val title: String,
    val issueNum: String,
    val creatorName: String,
    val creatorAvatarLink: String,
    val labels: List<LabelViewData> = emptyList(),
    val assignees: List<AssigneeViewData> = emptyList(),
    val status: String,
    val description: String,
    val comments: List<CommentViewData> = emptyList()
)

/**
 * - Lays out the component (title, labels, description, assignee, comment, etc.)
 * - It Use the Strategy design pattern idea to allow changes in the layout structure without affecting the content
 * - It is agnostic to the content itself
 * - Focuses on how the content is laid out,which follow the Single responsibility
 * - To use a different layout structure, only modifications to this part are necessary; the content remains unaffected
 * @param creatorInfo is the short of such as username, image link
 */

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun _IssueDetailsLayout(
    modifier: Modifier = Modifier,
    title: @Composable (Modifier) -> Unit,
    issueNumber: @Composable (Modifier) -> Unit,
    creatorInfo: @Composable (Modifier) -> Unit,
    labels: @Composable (Modifier) -> Unit,
    status: @Composable (Modifier) -> Unit,
    assignees: @Composable (Modifier) -> Unit,
    description: @Composable (Modifier) -> Unit,
    comments: @Composable (Modifier) -> Unit,
) {
    Column(modifier = modifier) {
        FlowRow {
            title(Modifier)
            issueNumber(Modifier)
        }
        VerticalGap_32Dp()
        FlowRow(modifier = Modifier) {
            status(Modifier)
            HorizontalGap_8Dp()
            creatorInfo(Modifier.align(Alignment.CenterVertically))
        }
        VerticalGap_32Dp()
        _Heading(
            text = stringResource(R.string.labels),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        VerticalSpace_8Dp()
        labels(Modifier)
        VerticalGap_32Dp()
        _Heading(
            text = stringResource(R.string.assignees),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        VerticalSpace_8Dp()
        assignees(Modifier)
        VerticalGap_32Dp()
        _Heading(
            text = stringResource(R.string.description),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        description(Modifier)
        VerticalGap_32Dp()
        _Heading(
            text = stringResource(R.string.comments),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        VerticalSpace_8Dp()
        comments(Modifier)
    }

}


/**Represent the issue number that show with issue title with lesser opacity*/
@Composable
private fun _IssueNumber(modifier: Modifier = Modifier, number: String) {
    TextWithLessOpacity(
        modifier = modifier,
        text = "#$number",
        style = MaterialTheme.typography.titleMedium
    )
}

/**Represent the status of issue(open, close or unknown)*/
@Composable
fun _IssueStatus(modifier: Modifier = Modifier, status: String) {
    val icon = when (status.lowercase(Locale.getDefault())) {
        "open" -> common.ui.R.drawable.ic_issue_opened
        "close" -> common.ui.R.drawable.ic_issue_closed
        else -> common.ui.R.drawable.ic_issue_status_unknown
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            //Github website issue status color= #1c7c3c,
            // Okay to use  this hardcoded color,because it  works well both in dark and light mode
            .background(Color(android.graphics.Color.parseColor("#1c7c3c")))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Image(
                painter = painterResource(icon),
                contentDescription = "issue status icon"
            )
            Spacer(Modifier.width(2.dp))
            Text(
                text = status.replaceFirstChar { it.uppercaseChar() },
                style = MaterialTheme.typography.labelLarge.copy(color = Color.White)
            )
        }

    }

}

/**
 * - Intended to be used as header section start,such as
 *  - Description _____
 * - Comment _______
 *
 */
@Composable
private fun _Heading(modifier: Modifier = Modifier, text: String) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = text,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.width(2.dp))
        HorizontalDivider()
    }

}

