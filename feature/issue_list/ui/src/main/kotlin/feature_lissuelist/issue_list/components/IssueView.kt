@file:Suppress("UnUsed", "ComposableNaming", "FunctionName")

package feature_lissuelist.issue_list.components
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import common.ui.LabelListView
import common.ui.LabelViewData
import common.ui.UserShortInfoView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
internal fun IssueView(
    modifier: Modifier = Modifier,
    info: IssueViewData,
    highlightedText: String?,
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
                text = if (highlightedText == null) AnnotatedString(info.title) else
                    SearcherHighlightedText().getHighLightedString(
                        info.title,
                        highlightedText
                    ),
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
//Helper methods
@SuppressLint("NewApi")
private fun _extractDateFromTimestamp(timestamp: String): String {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val dateTime = LocalDateTime.parse(timestamp, formatter)
    return dateTime.toLocalDate().toString()
}