package feature_issuedetails.details.route

import androidx.lifecycle.ViewModel
import common.ui.LabelViewData
import feature_issuedetails.details.components.AssigneeViewData
import feature_issuedetails.details.components.CommentViewData
import issue_details.di_container.DIFactory
import issue_details.domain.model.CommentModel
import issue_details.domain.model.IssueDetailsModel
import issue_details.domain.model.LabelModel
import issue_details.domain.model.UserShortInfoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


internal class IssueDetailsViewModel :ViewModel(),IssueDetailsViewController{
    private val _details = MutableStateFlow<IssueDetailsViewData?>(null)
    override val details = _details.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    override val isLoading = _isLoading.asStateFlow()

    /**either error or success message,can be shown using snackBar*/
    private val _screenMessage = MutableStateFlow<String?>(null)

    /**Should be used by Screen/Route component that is not making internal*/
    override val screenMessage = _screenMessage.asStateFlow()

    override suspend fun onDetailsRequest(issueNumber: String) {
        val response = DIFactory.createIssueDetailsRepository().fetchDetails(issueNumber)
        if (response.isSuccess) {
            updateState(response)
        } else {
            _updateScreenMessage("Failed to fetch details:${response.exceptionOrNull()}")
        }
    }


    /** taking in wrapping in Result so that exception can handle by this method*/
    private fun updateState(result: Result<IssueDetailsModel>) {
        try {
            val model = result.getOrThrow()
            _details.update {
                IssueDetailsViewData(
                    title = model.title,
                    issueNum = model.num,
                    creatorName = model.creator.username,
                    creatorAvatarLink = model.creator.avatarLink,
                    labels = model.labels.map(::toLabelViewData),
                    description = model.body,
                    status = model.status,
                    assignees = model.assigneeModel.map(::toAssigneeViewData),
                )
            }
        } catch (e: Exception) {
            _updateScreenMessage("Failed to fetch details+$e")
        }
    }


    //TODO:Helper method section-------------
    private fun toLabelViewData(model: LabelModel) = LabelViewData(
        name = model.name,
        hexCode = model.hexCode,
        description = model.description
    )

    private fun toAssigneeViewData(model: UserShortInfoModel) = AssigneeViewData(
        username = model.username, avatarLink = model.avatarLink
    )

    private fun toCommentViewData(model: CommentModel) = CommentViewData(
        creatorUsername = model.user.username,
        creatorAvatarLink = model.user.avatarLink,
        createdAt = model.createdAt,
        updatedAt = model.updatedAt,
        authorAssociation = model.authorAssociation,
        body = model.body
    )

    private fun _updateScreenMessage(msg: String?) {
        CoroutineScope(Dispatchers.Default).launch {
            _screenMessage.update { msg }
            delay(3000)
            _screenMessage.update { null }//clear message after 3 sec
        }

    }

}
