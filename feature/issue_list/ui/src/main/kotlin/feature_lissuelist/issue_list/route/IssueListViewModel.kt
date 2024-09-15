package feature_lissuelist.issue_list.route

import androidx.lifecycle.ViewModel
import common.ui.LabelViewData
import feature_lissuelist.issue_list.components.IssueListViewController
import feature_lissuelist.issue_list.components.IssueViewData
import issue_list.di_container.DIFactory
import issue_list.domain.model.IssueModel
import issue_list.domain.model.LabelModel
import issue_list.domain.repository.QueryType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IssueListViewModel: ViewModel(),IssueListViewController{
    private val _issues = MutableStateFlow<List<IssueViewData>?>(null)
    override val issues = _issues.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    override val isLoading = _isLoading.asStateFlow()

    /**either error or success message,can be shown using snackBar*/
    private val _screenMessage = MutableStateFlow<String?>(null)

    /**Should be used by Screen/Route component that is not making internal*/
    override val screenMessage = _screenMessage.asStateFlow()


    override suspend fun onIssueListRequest() {
        _isLoading.update { true }
        val response = DIFactory.createIssueListRepository().fetchIssues()
        if (response.isSuccess) {
            updateState(response)
        } else {

            _updateScreenMessage("Failed to fetch details:${response.exceptionOrNull()}")
        }
        _isLoading.update { false }
    }

    /** public so that outer module can use it to build search feature
     * @param ignoreKeyword the keyword that should ignore
     **/
    override suspend fun onIssueSearch(query: String, type: QueryType, ignoreKeyword: String) {
        _isLoading.update { true }
        val response = DIFactory.createIssueListRepository().fetchIssues(query, type, ignoreKeyword)
        if (response.isSuccess) {
            updateState(response)
        } else {

            _updateScreenMessage("Failed to fetch details:${response.exceptionOrNull()}")
        }
        _isLoading.update { false }
    }


    /** taking in wrapping in Result so that exception can handle by this method*/
    private fun updateState(result: Result<List<IssueModel>>) {
        try {
            _issues.update {
                result.getOrThrow().map(::toIssueViewData)
            }

        } catch (e: Exception) {
            _updateScreenMessage("Failed to fetch details:$e")
        }
    }


    //TODO:Helper method section-------------
    private fun toLabelViewData(model: LabelModel) = LabelViewData(
        name = model.name,
        hexCode = model.hexCode,
        description = model.description
    )

    private fun toIssueViewData(model: IssueModel) = IssueViewData(
        title = model.title,
        id = model.id,
        creatorName = model.creatorName,
        creatorAvatarLink = model.userAvatarLink,
        createdTime = model.createdTime,
        labels = model.labels.map(::toLabelViewData)
    )

    private fun _updateScreenMessage(msg: String?) {
        CoroutineScope(Dispatchers.Default).launch {
            _screenMessage.update { msg }
            delay(3000)
            _screenMessage.update { null }//clear message after 3 sec
        }

    }
}
