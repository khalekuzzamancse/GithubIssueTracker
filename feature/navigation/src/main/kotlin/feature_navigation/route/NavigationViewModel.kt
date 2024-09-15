package feature_navigation.route

import androidx.lifecycle.ViewModel
import feature_navigation.factory.NavigationFactory

internal class NavigationViewModel :ViewModel(){
    val navigationManager= NavigationFactory.createNavigationManager()
}