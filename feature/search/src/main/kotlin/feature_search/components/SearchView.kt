@file:Suppress("UnUsed", "ComposableNaming", "FunctionName")

package feature_search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import feature_search.R

/**
 * - It contain the option for taking input the search text should be ignored in search such as ignore "flutter" keyword
 * - It allow to take input a keyword that
 * @param query represent the text that will be searched, parent function should preserve it on configuration changed
 * @param ignoreKeyword should be ignored in search such as ignore "flutter" keyword, parent function should preserve it on configuration changed
 * @param onIgnoreKeywordChanged it pass the keyword that should ignore,parent function should preserve it on configuration changed
 */
@Composable
internal fun SearchView(
    onSearch: (String) -> Unit,
    query: String,
    onQueryChanged: (query: String) -> Unit,
    ignoreKeyword: String,
    onIgnoreKeywordChanged: (keyword: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    /**Show filter controllers such as filter by query type(title,description,etc)
     * and what keyword to ignore such as ignore the "flutter" keyword
     **/
    val showFilterOptions = query.isEmpty()
    TextField(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        value = query,
        onValueChange = onQueryChanged,

        placeholder = {
            Text(text = stringResource(R.string.search))
        },
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription ="search icon"
            )
        },
        trailingIcon = {
            if (showFilterOptions) {
                _SearchFilterOptions(
                    ignoreKeyword = ignoreKeyword,
                    onIgnoreKeywordChanged = onIgnoreKeywordChanged,

                    )
            } else {
                IconButton(onClick = {
                    onQueryChanged("")
                }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear Text")
                }
            }

        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(query)
            keyboardController?.hide() // Dismiss the keyboard
        }),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )

}

/** - Contain filter controllers such as filter by queryType(title,description,etc)
 * and what keyword to ignore such as ignore the "flutter" keyword
 * - TODO:Need to implement the queryType dropdown
 **/
@Composable
private fun _SearchFilterOptions(
    modifier: Modifier = Modifier,
    ignoreKeyword: String,
    onIgnoreKeywordChanged: (keyword: String) -> Unit
) {
    _FilterByKeyword(
        modifier = modifier,
        ignoreKeyword = ignoreKeyword,
        onIgnoreKeywordChanged = onIgnoreKeywordChanged
    )
}


/**
 * - It is intended to use the take input the keyword that should ignore in search
 * - It has icon button that is clickable
 * - On icon button click is open the [_IgnoreKeywordInputDialog]
 * - It manage and preserve the dialog state on configuration changed
 * - Usage recommendation: Should be as trailing icon of search bar
 */

@Composable
private fun _FilterByKeyword(
    modifier: Modifier = Modifier,
    ignoreKeyword: String,
    onIgnoreKeywordChanged: (keyword: String) -> Unit
) {
    /**Small state it's fine to preserve here instead of hoisting to reduce burden of
    the parent function or ViewModel.*/
    var showInputDialog by rememberSaveable { mutableStateOf(false) }

    IconButton(modifier = modifier, onClick = {
        showInputDialog = true
    }) {
        Icon(
            painter = painterResource(R.drawable.ic_block),
            contentDescription = "Filter issue"
        )
    }
    if (showInputDialog) {
        _IgnoreKeywordInputDialog(
            ignoreKeyword = ignoreKeyword,
            onDismissRequest = { showInputDialog = false },
            onConfirm = onIgnoreKeywordChanged
        )
    }

}

/**- A popup dialog that takes input a text that will be ignored in the search
- It manage and preserve the dialog state on configuration changed*/

@Composable
private fun _IgnoreKeywordInputDialog(
    modifier: Modifier = Modifier,
    ignoreKeyword: String,
    onConfirm: (keyword: String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    /**Small state it's fine to preserve here instead of hoisting to reduce burden of
    the parent function or ViewModel.*/
    var keyword by rememberSaveable { mutableStateOf(ignoreKeyword) }
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.ignore_keyword)) },
        text = {
            Column {
                TextField(
                    value = keyword,
                    onValueChange = { keyword = it },
                    singleLine = true,
                    placeholder = {
                        Text(stringResource(R.string.enter_a_keyword_to_ignore))
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(keyword)
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )

}
