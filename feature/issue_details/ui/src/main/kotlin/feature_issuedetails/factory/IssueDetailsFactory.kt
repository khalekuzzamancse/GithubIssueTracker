package feature_issuedetails.factory

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import feature_issuedetails.details.route.IssueDetailsViewController
import feature_issuedetails.details.route.IssueDetailsViewModel

/**
 * - Contain the different factory methods
 * - Client code or module should only get the instances via the factory method
 * for loose coupling and depends on abstraction
 * -  in future implementation should be
 * changed so directly depending on concretion will  cause tight coupling that is why recommend
 * to get the instance via factory method
 */
internal object IssueDetailsFactory {
    /**
     * - Provide the an instance of [IssueDetailsViewController]
     * - If in future need to change the implementation then just modify this
     * method,need to touch the client code that uses [IssueDetailsViewController]
     */
    @Composable
    fun createDetailsController(): IssueDetailsViewController = viewModel<IssueDetailsViewModel>()
}