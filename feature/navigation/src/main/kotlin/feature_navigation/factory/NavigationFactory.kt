package feature_navigation.factory

import feature_navigation.component.NavigationManager

/**
 * - Contain the different factory methods
 * - Client code or module should only get the instances via the factory method
 * for loose coupling and depends on abstraction
 * -  in future implementation should be
 * changed so directly depending on concretion will  cause tight coupling that is why recommend
 * to get the instance via factory method
 */
internal object NavigationFactory {
    /**
     * - Provide the an instance of [NavigationManager]
     * - If in future need to change the implementation then just modify this
     * method,need to touch the client code that uses [NavigationManager]
     */
    fun createNavigationManager(): NavigationManager =
        NavigationManagerImpl
}