@file:Suppress("UnUsed", "ComposableNaming")

package feature.issue_details.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.ui.TextWithLessOpacity
import common.ui.UserShortInfoView

/**
 * - Show the list of assignee for a particular issue
 * - also handle the case when there is no assignee
 */
@Composable
internal fun AssigneeListView(
    modifier: Modifier = Modifier,
    assignees: List<AssigneeViewData>,
    onUserProfileRequest: (String) -> Unit,
) {
    if (assignees.isEmpty())
        TextWithLessOpacity(text = "No one assigned")
    else
        _AssigneeListView(
            modifier = modifier,
            assignees = assignees,
            onUserProfileRequest=onUserProfileRequest
        )

}

/**Defined private method to denote list of assignee for non empty assignee list*/
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun _AssigneeListView(
    modifier: Modifier = Modifier,
    assignees: List<AssigneeViewData>,
    onUserProfileRequest: (String) -> Unit,
) {
    FlowRow(modifier = modifier) {
        assignees.forEach { assignee ->
            UserShortInfoView(
                username = assignee.username,
                avatarLink = assignee.avatarLink,
                onUsernameOrImageClick = { onUserProfileRequest(assignee.username) }
            )
        }
    }

}

/**
 * - Used represent the data or state a single assignee
 * - It will be used by the [AssigneeListView]
 * - It should not be used by outer module
 * - It designed to take converted data from corresponding model(defined in domain layer)
 */
internal data class AssigneeViewData(
    val username: String,
    val avatarLink: String
)