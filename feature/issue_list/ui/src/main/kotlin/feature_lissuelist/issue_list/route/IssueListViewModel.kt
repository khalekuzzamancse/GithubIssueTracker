@file:Suppress("FunctionName")

package feature_lissuelist.issue_list.route

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.ui.LabelViewData
import common.ui.SnackBarMessage
import feature_lissuelist.issue_list.components.IssueListViewController
import feature_lissuelist.issue_list.components.IssueViewData
import issue_list.di_container.DIFactory
import issue_list.domain.model.IssueModel
import issue_list.domain.model.LabelModel
import issue_list.domain.repository.QueryType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IssueListViewModel : ViewModel(), IssueListViewController {
    private val _issues = MutableStateFlow<List<IssueViewData>?>(null)
    override val issues = _issues.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    override val isLoading = _isLoading.asStateFlow()

    /**either error or success message,can be shown using snackBar*/
    private val _screenMessage = MutableStateFlow<SnackBarMessage?>(null)

    /**Should be used by Screen/Route component that is not making internal*/
    override val screenMessage = _screenMessage.asStateFlow()

    init {
        //On the first time it fetch the data
        viewModelScope.launch {
            _isLoading.update { true }
            _fetchIssues()
            _isLoading.update { false }
        }
    }

    /** public so that outer module can use it to build search feature
     * @param ignoreKeyword the keyword that should ignore
     **/
    override suspend fun onIssueSearch(query: String, type: QueryType, ignoreKeyword: String) {
        _isLoading.update { true }
        _queryIssues(query, type, ignoreKeyword)
        _isLoading.update { false }
    }


    override fun onScreenMessageDismissRequest() {
        _screenMessage.update { null }
    }


    private suspend fun _fetchIssues() {
        val result = DIFactory.createIssueListRepository().fetchIssues()
        if (result.isSuccess)
            _updateIssueList(result)
        else
            _updateMessageOnException(result.exceptionOrNull())
    }

    private suspend fun _queryIssues(query: String, type: QueryType, ignoreKeyword: String) {
        val result = DIFactory.createIssueListRepository().queryIssues(query, type, ignoreKeyword)
        if (result.isSuccess) {
            _updateIssueList(result)
        } else {
            _updateMessageOnException(result.exceptionOrNull())
        }
    }

    /** taking in wrapping in Result so that exception can handle by this method*/
    private fun _updateIssueList(result: Result<List<IssueModel>>) {
        try {
            _issues.update {
                result
                    .getOrThrow()
                    .map { it._toIssueViewData() }
            }

        } catch (e: Exception) {
            _updateMessageOnException(e)
        }
    }

    private fun _updateMessageOnException(exception: Throwable?) {
        val ex = exception ?: Throwable(
            message = "Error...",
            cause = Throwable("Unknown reason at ${this.javaClass.name}")
        )
        _screenMessage.update {
            SnackBarMessage(
                message = ex.message.toString(),
                details = ex.cause?.message
            )
        }
    }

}




//TODO:Helper method section------------- TODO:Helper method section-------------
private fun IssueModel._toIssueViewData() = IssueViewData(
    title = this.title,
    id = this.id,
    creatorName = this.creatorName,
    creatorAvatarLink = this.userAvatarLink,
    createdTime = this.createdTime,
    labels = this.labels.map { it._toLabelViewData() }
)

private fun LabelModel._toLabelViewData() = LabelViewData(
    name = this.name,
    hexCode = this.hexCode,
    description = this.description
)