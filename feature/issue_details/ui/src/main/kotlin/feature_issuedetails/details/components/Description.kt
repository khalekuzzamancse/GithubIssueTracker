@file:Suppress("UnUsed", "ComposableNaming")

package feature_issuedetails.details.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import common.ui.TextWithLessOpacity
import dev.jeziellago.compose.markdowntext.MarkdownText
import feature_issuedetails.R


/**
 * - Represent the body/description of issue
 * @param body represent the markdown as string
 **/
@Composable
internal fun Description(modifier: Modifier = Modifier, body: String) {
    if (body.isEmpty())
        TextWithLessOpacity(text = stringResource(R.string.no_description_found))
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
