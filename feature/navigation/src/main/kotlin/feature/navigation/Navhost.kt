package feature.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import feature.issue_details.routes.IssueDetailsRoute
import feature.issue_list.components.IssueListViewController
import feature.issue_list.route.IssuesListRoute
import feature.search.route.IssuesSearchRoute
import kotlinx.serialization.Serializable

@Composable
internal fun RootNavHost(
    modifier: Modifier = Modifier,
    navigateTo: Destination?,
    onDestinationChanged: (Destination) -> Unit
) {
    val controller = remember { IssueListViewController() }
    val searchController = remember { IssueListViewController() }
    val navController = rememberNavController()
    val entry by navController.currentBackStackEntryAsState()
    LaunchedEffect(entry) {
        entry.let {
            val route = "${it?.destination?.route}"
            if (route.contains(Route.IssueList.javaClass.simpleName))
                onDestinationChanged(Destination.IssueList)
            else if (route.contains(Route.IssueDetails::class.java.simpleName))
                onDestinationChanged(Destination.IssueDetails)
            else if (route.contains(Route.Search.javaClass.simpleName))
                onDestinationChanged(Destination.Search)
        }

    }
    LaunchedEffect(navigateTo) {
        navigateTo?.let { destination ->
            when (destination) {
                Destination.IssueList -> navController.navigate(Route.IssueList)
                Destination.Search -> navController.navigate(Route.Search)
                else -> {}
            }
        }

    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.IssueList
    ) {
        composable<Route.IssueList> {
            IssuesListRoute(
                controller = controller,
                onDetailsRequest = { issueNum ->
                    navController.navigate(
                        Route.IssueDetails(
                            issueNumber = issueNum
                        )
                    )
                    Log.d("InfoRe::", "Details")
                },
                onUserProfileRequest = {
                    Log.d("InfoRe::", "user")
                }
            )
        }
        composable<Route.IssueDetails> {
            val issueNumber = it.toRoute<Route.IssueDetails>().issueNumber
            IssueDetailsRoute(
                issueNum = issueNumber,
                onUserProfileRequest = {},
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(
                        rememberScrollState()
                    ),
            )
        }
        composable<Route.Search> {
            IssuesSearchRoute(
                controller = searchController,
                onDetailsRequest = {
                    Log.d("InfoRe::", "Details")
                },
                onUserProfileRequest = {
                    Log.d("InfoRe::", "user")
                }
            )

        }

    }

}

interface Route {
    object None

    @Serializable
    object IssueList : Route

    @Serializable
    data class IssueDetails(val issueNumber: String) : Route

    @Serializable
    object Search : Route

}

enum class Destination {
    None, IssueList, IssueDetails, Search
}