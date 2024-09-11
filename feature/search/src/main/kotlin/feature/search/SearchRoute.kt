package feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchRoute(modifier: Modifier = Modifier) {
    SearchView(
        onExitRequest = {},
        barLeadingIcon = { },
        items = listOf(
            Employee("abul", "cse student"),
            Employee("babul", "eee teacher")
        ),
        filterPredicate = { employee, queryText ->
            val filter = (employee.name.contains(queryText, ignoreCase = true)
                    || employee.details.contains(queryText, ignoreCase = true))
            filter
        },
        searchedItemDecorator = { employee, queryText ->
            Column {
                Text(SearcherHighlightedText().getHighLightedString(employee.name, queryText))
                Text(SearcherHighlightedText().getHighLightedString(employee.details, queryText))
            }
        },

        )

}

data class Employee(
    val name: String,
    val details: String
)