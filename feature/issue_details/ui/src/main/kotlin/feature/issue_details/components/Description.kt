@file:Suppress("UnUsed", "ComposableNaming")

package feature.issue_details.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.ui.TextWithLessOpacity
import dev.jeziellago.compose.markdowntext.MarkdownText

/**
 * - Represent the body/description of issue
 * @param body represent the markdown as string
 **/
@Composable
internal fun Description(modifier: Modifier = Modifier, body: String) {
    if (body.isEmpty())
        TextWithLessOpacity(text = "No description found")
    else
        _Description(modifier = modifier, body = body)
}

/**
 * - Intended to be used for non empty body/description of the issue
 * @param body represent the markdown as string
 * */
@Composable
private fun _Description(modifier: Modifier = Modifier, body: String) {
    MarkdownText(
        markdown = body.replace("\\r\\n", "\n"),
        modifier = modifier
    )
}
