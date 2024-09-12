package feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
) {

    val navigationManager = remember { NavigationManager }
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
                onNavigationRequest = {route->
                    navigationManager.navigateTo(route,navHostController)
                }
            )
        }
    )


}


