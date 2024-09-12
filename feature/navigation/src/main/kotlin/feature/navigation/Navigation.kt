package feature.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
) {
    var selected by remember { mutableStateOf<Int?>(null) }
    NavigationLayoutDecorator(
        modifier = modifier,
        navigationItems = createNonExpandedDestination(),
        selected = selected,
        onDestinationSelected = {
            selected=it
        },
        content = {
            RootNavHost(
                onDestinationChanged = { destination ->
                    selected = toSelectedItem(destination)
                },
                navigateTo = when (selected) {
                    0 -> Destination.IssueList
                    1 -> Destination.Search
                    else -> Destination.None
                }
            )
        }
    )


}

private fun toSelectedItem(destination: Destination): Int? = when (destination) {
    Destination.None -> null
    Destination.IssueList -> 0
    Destination.IssueDetails -> null
    Destination.Search -> 1
}

private fun createNonExpandedDestination(): List<NavigationItem> {
    return listOf(
        NavigationItem(
            label = "Issues",
            focusedIcon = Icons.AutoMirrored.Filled.List,
            unFocusedIcon = Icons.AutoMirrored.Outlined.List,
        ),
        NavigationItem(
            label = "Search",
            focusedIcon = Icons.Default.Search,
            unFocusedIcon = Icons.Outlined.Search,
        )
    )
}
