@file:Suppress("UnUsed", "ComposableNaming")

package feature_issuedetails.details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import common.ui.TextWithLessOpacity
import common.ui.UserShortInfoView
import dev.jeziellago.compose.markdowntext.MarkdownText
import feature_issuedetails.R
import issue_details.di_container.DIFactory


/**
 * - Show the list of comments for a particular issue
 * - also handle the case when there is no comment
 */
@Composable
internal fun CommentListView(
    modifier: Modifier = Modifier,
    comments: List<CommentViewData> = emptyList(),
    onUserProfileRequest: (String) -> Unit,
) {
    if (comments.isEmpty())
        TextWithLessOpacity(modifier = modifier, text = stringResource(R.string.no_comment_found))
    else
        _CommentListView(
            modifier = modifier,
            comments = comments,
            onUserProfileRequest = onUserProfileRequest
        )


}

/**Defined private method to denote list of comment for non empty comment list*/
@Composable
private fun _CommentListView(
    modifier: Modifier = Modifier,
    comments: List<CommentViewData>,
    onUserProfileRequest: (String) -> Unit,
) {
    /**Comment list is not large enough so it is okay to not use lazy list*/
    Column(modifier = modifier) {
        comments.forEach { comment ->
            _CommentView(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        border = BorderStroke(width = 1.dp, color = Color.Black),
                        shape = RoundedCornerShape(8.dp)
                    ),
                data = comment,
                onUserProfileRequest = { onUserProfileRequest(comment.creatorUsername) }
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

/**
 * - Represent a single comment
 */

@Composable
private fun _CommentView(
    modifier: Modifier = Modifier,
    data: CommentViewData,
    onUserProfileRequest: () -> Unit,
) {
    Surface(
        modifier = modifier//margin
        , shadowElevation = 4.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            _Header(
                modifier = Modifier.fillMaxWidth(),
                creatorUsername = data.creatorUsername,
                creatorAvatarLink = data.creatorAvatarLink,
                onUserProfileRequest = onUserProfileRequest
            )
            Spacer(Modifier.height(8.dp))
            MarkdownText(
                markdown = data.body.replace("\\r\\n", "\n"),
                modifier = Modifier.padding(8.dp)
            )
        }
    }


}

/** Represent the top section of a comment item,designed according to github website*/
@Composable
private fun _Header(
    modifier: Modifier = Modifier,
    creatorUsername: String,
    creatorAvatarLink: String,
    onUserProfileRequest: () -> Unit,
) {
    Surface(modifier = modifier, tonalElevation = 8.dp) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            UserShortInfoView(//From common:ui module
                username = creatorUsername,
                avatarLink = creatorAvatarLink,
                modifier = Modifier,
                onUsernameOrImageClick = onUserProfileRequest
            )
        }
    }

}

/**
 * - Used represent the data or state a single comment
 * - It will be used by the [_CommentView]
 * - It should not be used by outer module
 * - It designed to take converted data from corresponding model(defined in domain layer)
 */
internal data class CommentViewData(
    val creatorUsername: String,
    val creatorAvatarLink: String,
    val createdAt: String,
    val updatedAt: String?,
    val body: String,
)