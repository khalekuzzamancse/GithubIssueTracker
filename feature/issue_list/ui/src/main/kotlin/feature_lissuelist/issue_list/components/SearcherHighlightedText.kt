package feature_lissuelist.issue_list.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration

/**
 * - Used to highlight text
 * - Helpful for finding searched text
 * - Can be used for other purpose to highlighting text
 * - Example uses
 * ```kotlin
 *    Text(SearcherHighlightedText().getHighLightedString(wholeText, queryText))
 * ```
 */

class SearcherHighlightedText {
    fun getHighLightedString(text: String, highLightedText: String): AnnotatedString {
        val annotatedEmailString: AnnotatedString = buildAnnotatedString {
            append(text)
            val urls =findText(text, highLightedText)
            urls.forEach { pair ->
                addStyle(
                    style = SpanStyle(
                        background = Color.Yellow,
                        textDecoration = TextDecoration.None
                    ),
                    start = pair.first,
                    end = pair.second + 1
                )
            }
        }
        return annotatedEmailString
    }

    private fun findText(text: String, key: String): List<Pair<Int, Int>> {

        val originalText=text.lowercase()
        val searchText=key.lowercase().toRegex()
        val indices = mutableListOf<Pair<Int, Int>>()

        searchText.findAll(originalText).forEach { matchResult ->
            val startIndex = matchResult.range.first
            val endIndex = matchResult.range.last
            indices.add(startIndex to endIndex)
        }
        return indices
    }
}