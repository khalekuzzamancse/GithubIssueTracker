@file:Suppress("ComposableNaming","UnUsed")
package common.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * - SearchBar , used Strategy Design pattern so need to fill some slot in order to use
 * - It is reusable across different project that is why putting all component in a single file so that easily can copy-paste to other project
 * - Usage Example:
 *
 * ```kotlin
 * SearchView(
 *     onExitRequest = {},
 *     barLeadingIcon = {},
 *     items = listOf(
 *         Employee("Mr John", "CSE Student"),
 *         Employee("Mr Bean", "EEE Teacher")
 *     ),
 *     filterPredicate = { employee, queryText ->
 *         val filter = employee.name.contains(queryText, ignoreCase = true) ||
 *                      employee.details.contains(queryText, ignoreCase = true)
 *         filter
 *     },
 *     searchedItemDecorator = { employee, queryText ->
 *         Column {
 *             Text(SearcherHighlightedText().getHighLightedString(employee.name, queryText))
 *             Text(SearcherHighlightedText().getHighLightedString(employee.details, queryText))
 *         }
 *     }
 * )
 * ```
 */

@Composable
fun <T> SearchView(
    items: List<T>,
    filterPredicate: (T, String) -> Boolean,
    barLeadingIcon: @Composable () -> Unit = {},
    onSearch: (String) -> Unit = {},
    onExitRequest: () -> Unit,
    searchedItemDecorator: @Composable (T, highLightedText: String) -> Unit,
) {
    val uiState = remember(items) {
        SearchDecoratorState(
            items,
            filterPredicate
        )
    }
    val active = uiState.active.collectAsState().value
    SearchView(
        query = uiState.query.collectAsState().value,
        onQueryChanged = uiState::onQueryChanged,
        onActiveChanged = {
            if (!it)
                onExitRequest()
        },
        result = uiState.results.collectAsState().value,
        searchedItemDecorator = searchedItemDecorator,
        barLeadingIcon = barLeadingIcon,
        onSearch = onSearch
    )


}

class SearchDecoratorState<T>(
    private val items: List<T>,
    val predicate: (T, String) -> Boolean,
) {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    private val _results = MutableStateFlow(items)
    val results = _results.asStateFlow()
    fun onQueryChanged(query: String) {
        _query.update { query }
        _results.value = results.value.filter { predicate(it, query) }
        if (query == "") {
            _results.value = items
        }
    }

    private val _active = MutableStateFlow(true)
    val active = _active.asStateFlow()
    fun onActiveChanged(status: Boolean) {
        _active.value = status

    }

    private val _showSearch = MutableStateFlow(true)
    fun onSearchChanged(status: Boolean) {
        _showSearch.value = status
        if (!status) {
            clear()
        }
    }

    private fun clear() {
        _results.value = items
        _query.value = ""

    }

}

@Composable
private fun <T> SearchView(
    query: String,
    onQueryChanged: (String) -> Unit,
    onActiveChanged: (Boolean) -> Unit,
    result: List<T>,
    barLeadingIcon: @Composable () -> Unit = {},
    onSearch: (String) -> Unit = {},
    searchedItemDecorator: @Composable (T, String) -> Unit,
) {
    _MySearchBar(
        query = query,
        onQueryChange = onQueryChanged,
        active = true,
        barLeadingIcon = barLeadingIcon,
        onSearch = onSearch,
        onActiveChanged = onActiveChanged,
        modifier = Modifier,
    ) {
        LazyColumn {
            items(items = result) { item ->
                searchedItemDecorator(item, query)
            }
        }
    }


}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun _MySearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit={},
    active: Boolean,
    onActiveChanged: (Boolean)-> Unit,
    barLeadingIcon: @Composable ()->Unit={},
    onSearch:(String)->Unit={},
    content:@Composable ColumnScope.()->Unit={},
) {
    SearchBar(
        modifier = modifier.fillMaxWidth(),
        query = query,
        enabled = true,
        onQueryChange =onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChanged,
        placeholder = {
            Text(text = "Type here  to to search")
        },
        shape = RectangleShape,
        leadingIcon = barLeadingIcon, trailingIcon = {
            if (query.isNotBlank()) {
                IconButton(
                    onClick = {
                        onQueryChange("")
                    }
                ){
                    Icon(
                        imageVector = Icons.Default.Clear,
                        null
                    )
                }
            }

        },
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun _MySearchBar(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit={},
    query: String,
    onQueryChange: (String) -> Unit={},
    active: Boolean,
    onActiveChanged: (Boolean)-> Unit,
    content:@Composable ColumnScope.()->Unit={},
) {

    SearchBar(
        modifier = modifier.fillMaxWidth(),
        query = query,
        onQueryChange =onQueryChange,
        onSearch = {
            // onActiveChanged(false)
        },
        active = active,
        onActiveChange = onActiveChanged,
        placeholder = {
            Text(text = "Type here  to to search")
        },
        shape = RectangleShape,
        leadingIcon = {
            IconButton(
                onClick = {
                    // onActiveChanged(false) #disabling the exits
                    onGoBack()
                }
            ){
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    null
                )
            }

        }, trailingIcon = {
            if (query.isNotBlank()) {
                IconButton(
                    onClick = {
                        onQueryChange("")
                    }
                ){
                    Icon(
                        imageVector = Icons.Default.Clear,
                        null
                    )
                }
            }

        },
        content = content
    )
}

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