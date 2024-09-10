package com.kzcse.githubissuetracker

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
    onDetailsRequest: (id: String) -> Unit = {},
) {
    var issuses by remember { mutableStateOf<List<IssueInfo>?>(null) }
    LaunchedEffect(Unit) {
        val response = APIFacade().requestIssueList()
        if (response.isSuccess) {
            issuses = response.getOrNull()?.let { issueList ->
                issueList
                    .filter { issue ->
                        issue.title != null
                    }
                    .map { issue ->
                        exampleIssue.copy(
                            title = issue.title!!,
                            createdTime = issue.createdAt!!,
                            userAvatarLink = issue.user!!.avatarUrl!!,
                            creatorName = issue.user!!.login!!,
                            labels = issue.labelEntities.map { model ->
                                Label(
                                    name = model.name!!,
                                    hexCode = model.color!!
                                )
                            }
                        )
                    }
            }
        }
    }
//    IssuesList(
//        modifier = Modifier,
//        issues = listOf(exampleIssue, exampleIssue, exampleIssue)
//    )
    issuses?.let {
        IssuesList(
            modifier = Modifier,
            issues = it
        )
    }
}


@Composable
fun IssuesList(
    modifier: Modifier = Modifier,
    issues: List<IssueInfo>
) {
    LazyColumn(modifier) {
        itemsIndexed(issues) { index, issue ->
            val isNotLastItem = (index != issues.lastIndex)
            _Issue(
                modifier = Modifier.padding(8.dp),
                info = issue
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
fun _Issue(
    modifier: Modifier = Modifier,
    info: IssueInfo,
) {
    val labelColor = MaterialTheme.typography.labelMedium.color
    val labelStyle = MaterialTheme.typography.labelMedium.copy(
        color = labelColor.copy(alpha = 0.6f)
    )
    _IssueComponentSlot(
        modifier = modifier,
        title = {
            Text(
                modifier = it,
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
            )
        },
        userName = {
            Text(
                modifier = it,
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


//"https://avatars.githubusercontent.com/u/91824168?v=4"
data class IssueInfo(
    val id: String,
    val title: String,
    val createdTime: String,
    val userAvatarLink: String,
    val creatorName: String,
    val labels: List<Label> = emptyList(),
)

@SuppressLint("ComposableNaming")
@Composable
private fun _LabelView(
    modifier: Modifier = Modifier,
    label: Label
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(_hexToColor(label.hexCode))
            .padding(2.dp)
    ) {
        Text(text = label.name)
    }

}

data class Label(
    val name: String,
    val hexCode: String,
)

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