@file:Suppress("UnUsed", "FunctionName")

package feature_lissuelist.factory
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import feature_lissuelist.issue_list.components.IssueListViewController
import feature_lissuelist.issue_list.route.IssueListViewModel

/**
 * - Contain the different factory methods
 * - Client code or module should only get the instances via the factory method
 * for loose coupling and depends on abstraction
 * -  in future implementation should be
 * changed so directly depending on concretion will  cause tight coupling that is why recommend
 * to get the instance via factory method
 */
object IssueListFactory {
    /**
     * - Provide the an instance of [IssueListViewController]
     * - If in future need to change the implementation then just modify this
     * method,need to touch the client code that uses [IssueListViewController]
     */
    @Composable
    fun createIssueListController(): IssueListViewController = viewModel<IssueListViewModel>()
}