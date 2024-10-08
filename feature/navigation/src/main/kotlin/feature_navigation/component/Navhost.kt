package feature_navigation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import common.ui.SnackBarMessage
import feature_issuedetails.details.route.IssueDetailsRoute
import feature_lissuelist.issue_list.route.IssuesListRoute
import feature_search.route.IssuesSearchRoute
import kotlinx.serialization.Serializable

@Composable
internal fun RootNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigationRequest:(Route)->Unit,
    onScreenMessageUpdate:(SnackBarMessage)->Unit,
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.IssueList
    ) {
        composable<Route.IssueList> {
            IssuesListRoute(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(),
                onDetailsRequest = { issueNum -> onNavigationRequest(Route.IssueDetails(issueNum)) },
                onUserProfileRequest = {},
                onScreenMessageUpdate = onScreenMessageUpdate
            )
        }
        composable<Route.IssueDetails> {
            val issueNumber = it.toRoute<Route.IssueDetails>().issueNumber
            IssueDetailsRoute(
                issueNum = issueNumber,
                onUserProfileRequest = {},
                onScreenMessageUpdate = onScreenMessageUpdate,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(),
            )
        }
        composable<Route.Search> {
            IssuesSearchRoute(
                onDetailsRequest = {issueNum-> onNavigationRequest(Route.IssueDetails(issueNum)) },
                onUserProfileRequest = {},
                onScreenMessageUpdate = onScreenMessageUpdate
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

