package feature.issue_list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import issue_list.di_container.DIFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val exampleIssue = IssueInfo(
    id = "12345",
    title = "Unexpected behavior in jjjkjjjjkjjkk",
    createdTime = "2023-09-10",
    userAvatarLink = "https://avatars.githubusercontent.com/u/91824168?v=4",
    creatorName = "johndoe"
)


@Composable
fun IssuesListRoute(
    modifier: Modifier = Modifier,
    onDetailsRequest: (id: String) -> Unit,
    onCreatorInfoRequest: (userName: String) -> Unit,
) {
    var issuses by remember { mutableStateOf<List<IssueInfo>?>(null) }
    LaunchedEffect(Unit) {
        val response = DIFactory.createIssueListRepository().fetchIssues()
        if (response.isSuccess) {
            issuses = response.getOrNull()?.let { issueList ->
                issueList
                    .map { issue ->
                        exampleIssue.copy(
                            title = issue.title,
                            createdTime = issue.createdTime,
                            userAvatarLink = issue.userAvatarLink,
                            creatorName = issue.creatorName,
                            labels = issue.labels.map { model ->
                                Label(
                                    name = model.name,
                                    hexCode = model.hexCode,
                                    description = model.description
                                )
                            }
                        )
                    }
            }
        }
    }

    issuses?.let {
        IssuesList(
            modifier = Modifier,
            issues = it,
            onDetailsRequest = onDetailsRequest,
            onCreatorInfoRequest = onCreatorInfoRequest,
        )
    }
}


@Composable
fun IssuesList(
    modifier: Modifier = Modifier,
    issues: List<IssueInfo>,
    onDetailsRequest: (id: String) -> Unit,
    onCreatorInfoRequest: (userName: String) -> Unit,
) {
    LazyColumn(modifier) {
        itemsIndexed(issues) { index, issue ->
            val isNotLastItem = (index != issues.lastIndex)
            _IssueView(
                modifier = Modifier.padding(8.dp),
                info = issue,
                onDetailsRequest = { onDetailsRequest(issue.id) },
                onCreatorInfoRequest = { onCreatorInfoRequest(issue.creatorName) }
            )
            if (isNotLastItem) {
                HorizontalDivider()
            }


        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("ComposableNaming")
@Composable
fun _IssueView(
    modifier: Modifier = Modifier,
    info: IssueInfo,
    onDetailsRequest: () -> Unit,
    onCreatorInfoRequest: () -> Unit,
) {
    val labelColor = MaterialTheme.typography.labelMedium.color
    val labelStyle = MaterialTheme.typography.labelMedium.copy(
        color = labelColor.copy(alpha = 0.6f)
    )
    _IssueComponentSlot(
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
        userAvatar = {
            AsyncImage(
                model = info.userAvatarLink,
                contentDescription = "user image",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .clickable {
                        onCreatorInfoRequest()
                    }
            )
        },
        userName = {
            Text(
                modifier = it.clickable { onCreatorInfoRequest() },
                text = info.creatorName,
                style = labelStyle
            )
        },
        labels = if (info.labels.isNotEmpty()) {
            @Composable {
                FlowRow(
                    modifier = modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    info.labels.forEach { label ->
                        _LabelView(label = label)
                    }
                }
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
data class IssueInfo(
    val id: String,
    val title: String,
    val createdTime: String,
    val userAvatarLink: String,
    val creatorName: String,
    val labels: List<Label> = emptyList(),
)

data class Label(
    val name: String,
    val hexCode: String,
    val description: String?,
)

@SuppressLint("ComposableNaming")
@Composable
private fun _LabelView(
    modifier: Modifier = Modifier,
    label: Label
) {
    var showDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(_hexToColor(label.hexCode))
            .padding(2.dp)
            .clickable {
                showDialog = true
            }
    ) {
        Text(text = label.name)
    }
    if (showDialog) {
        _DescriptionDialog(
            description = label.description ?: "No description found",
            onDismissRequest = {
                showDialog = false
            })
    }


}

@Composable
private fun _DescriptionDialog(
    description: String,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = { Text(text = description) },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("OK")
            }
        }
    )
}


private fun _hexToColor(hexColor: String): androidx.compose.ui.graphics.Color {
    // Ensure the hex string has a '#' and is 7 characters long (including '#')
    val colorString = if (hexColor.startsWith("#")) hexColor else "#$hexColor"
    // Parse the color string to a long and create a Color object
    return Color(android.graphics.Color.parseColor(colorString))
}

/**
 * - This composable is only responsible for layout the component without worrying about look and feel of
 * component, this ensure single responsibility
 * - Following Strategy Design pattern so that using slot
 * @param title is the title  of the issue
 * @param userAvatar id of the issue
 * @param timestamp when the issue opened
 * @param labels label list may be empty that is why making nullable to avoid unnecessary margin/padding
 */
@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("ComposableNaming")
@Composable
private fun _IssueComponentSlot(
    modifier: Modifier = Modifier,
    title: @Composable (Modifier) -> Unit,
    timestamp: @Composable (Modifier) -> Unit,
    userAvatar: @Composable (Modifier) -> Unit,
    userName: @Composable (Modifier) -> Unit,
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            userAvatar(Modifier)
            Spacer(Modifier.width(8.dp))
            userName(Modifier)
        }
        if (labels != null) {
            Spacer(Modifier.height(4.dp))
            labels(Modifier)
        }

    }

}