package feature.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object NavigationManager {
    private val _selectedNavItem = MutableStateFlow<Int?>(null)
    val selectedNavItem = _selectedNavItem.asStateFlow()

    fun onNavItemClicked(selectedNavItem: Int, navController: NavHostController) {
        when (selectedNavItem) {
            0 -> navController.navigate(Route.IssueList)
            1 -> navController.navigate(Route.Search)
        }
    }

    fun onNavHostDestinationChanged(entry: NavBackStackEntry?) {
        val route = entry?.destination?.route ?: ""
        if (route.contains(Route.IssueList.javaClass.simpleName))
            _selectedNavItem.update { 0 }
        else if (route.contains(Route.Search.javaClass.simpleName))
            _selectedNavItem.update { 1 }
        else
            _selectedNavItem.update { null }
    }
    fun  navigateTo(route: Route, navController: NavHostController){
        navController.navigate(route)
    }


      val navItems= listOf(
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