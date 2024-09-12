package feature.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import feature.issue_details.routes.IssueDetailsRoute
import feature.issue_list.components.IssueListViewController
import feature.issue_list.route.IssuesListRoute
import feature.search.route.IssuesSearchRoute
import kotlinx.serialization.Serializable

@Composable
internal fun RootNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigationRequest:(Route)->Unit,
) {
    val controller = remember { IssueListViewController() }
    val searchController = remember { IssueListViewController() }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.IssueList
    ) {
        composable<Route.IssueList> {
            IssuesListRoute(
                controller = controller,
                onDetailsRequest = { issueNum ->
                    onNavigationRequest(Route.IssueDetails(issueNum))
                },
                onUserProfileRequest = {
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
                onDetailsRequest = {issueNum->
                    onNavigationRequest(Route.IssueDetails(issueNum))
                },
                onUserProfileRequest = {
                }
            )

        }

    }

}

interface Route {

    @Serializable
    object IssueList : Route

    @Serializable
    data class IssueDetails(val issueNumber: String) : Route

    @Serializable
    object Search : Route

}

