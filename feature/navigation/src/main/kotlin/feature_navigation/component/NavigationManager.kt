package feature_navigation.component

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.StateFlow

/**
 * - It help to manage the single source of truth for managing the navigation event
 * - It should also sync the selected  item based of the current route
 * - When request for navigate to new destination via bottom or nav rail or drawer item clicked, it
 * immediately should not mark the clicked nav item as selected,instead nav item should selected if navigation
 * is successful
 */
internal interface NavigationManager {
    /**The navigation items that will show in bottom bar or Navigation Rail or Drawer*/
    val navItems: List<NavigationItem>

    /**- The index of selected navigation item
     * - It nullable because on app start it might possible that no item is selected
     * -it should be selected based on click
     * because  it might possible that item is clicked but navigation is failed
     * in that case it will causes inconsistency. that is why based on the current route
     * in backstack the nav item should be selected(instead of clicked)
     * */
    val selectedNavItem: StateFlow<Int?>

    /**
     * - Response the event when a navigation item is clicked for navigation
     * - It should try to navigate to desired destination based on item clicked
     * - It should not update the [selectedNavItem] because it might possible that navigation
     *  to desire destination is failed or delayed
     *  @param navController used to navigation to avoid memory leak taking it on demand
     **/
    fun onNavItemClicked(selectedNavItem: Int, navController: NavHostController)

    /**
     * - Response the event when back stack is changed such as for forward or backward navigation
     * - It also update the [selectedNavItem] because route is changed
     * @param entry using it this will determine which route is selected and update the [selectedNavItem]
     **/
    fun onNavHostDestinationChanged(entry: NavBackStackEntry?)

    /**
     * - It will handle the navigation for the destination that are not present in the
     * bottom bar or navigation rail or drawer, means it navigate to non top destination
     *@param navController used to navigation to avoid memory leak taking it on demand
     */
    fun navigateTo(route: Route, navController: NavHostController)
}


