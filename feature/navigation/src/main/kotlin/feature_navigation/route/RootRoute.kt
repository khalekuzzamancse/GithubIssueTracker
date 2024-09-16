package feature_navigation.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import common.ui.SnackBarMessage
import feature_navigation.component.NavigationLayoutDecorator
import feature_navigation.component.RootNavHost


@Composable
 fun Navigation(
    modifier: Modifier = Modifier,
    onScreenMessageUpdate:(SnackBarMessage)->Unit,
) {
    val viewModel = viewModel<NavigationViewModel>()
    val navigationManager = viewModel.navigationManager
    val navHostController = rememberNavController()
    val entry by navHostController.currentBackStackEntryAsState()

    LaunchedEffect(entry) {
        navigationManager.onNavHostDestinationChanged(entry)
    }

    NavigationLayoutDecorator(
        modifier = modifier,
        navigationItems = navigationManager.navItems,
        selected = navigationManager.selectedNavItem.collectAsState().value,
        onDestinationSelected = { navItemIndex ->
            navigationManager.onNavItemClicked(navItemIndex, navHostController)
        },
        content = {
            RootNavHost(
                navController = navHostController,
                onNavigationRequest = { route -> navigationManager.navigateTo(route, navHostController) },
                onScreenMessageUpdate = onScreenMessageUpdate
            )
        }
    )


}


