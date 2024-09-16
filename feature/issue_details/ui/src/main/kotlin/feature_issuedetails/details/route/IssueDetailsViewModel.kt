@file:Suppress("FunctionName", "VariableName")

package feature_issuedetails.details.route

import androidx.lifecycle.ViewModel
import common.ui.LabelViewData
import common.ui.SnackBarMessage
import common.ui.SnackBarMessageType
import feature_issuedetails.details.components.AssigneeViewData
import feature_issuedetails.details.components.CommentViewData
import issue_details.di_container.DIFactory
import issue_details.domain.model.CommentModel
import issue_details.domain.model.IssueDetailsModel
import issue_details.domain.model.LabelModel
import issue_details.domain.model.UserShortInfoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


internal class IssueDetailsViewModel : ViewModel(), IssueDetailsViewController {
    private val _details = MutableStateFlow<IssueDetailsViewData?>(null)
    override val details = _details.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    override val isLoading = _isLoading.asStateFlow()


    /**either error or success message,can be shown using snackBar*/
    private val _screenMessage = MutableStateFlow<SnackBarMessage?>(null)

    /**Should be used by Screen/Route component that is not making internal*/
    override val screenMessage = _screenMessage.asStateFlow()


    override suspend fun onDetailsRequest(issueNumber: String) {
        val result = DIFactory.createIssueDetailsRepository().fetchDetails(issueNumber)
        if (result.isSuccess) {
            _updateDetails(result)
            _updateComments(issueNumber)
        } else
            _updateMessageOnException(result.exceptionOrNull())


    }


    override fun onScreenMessageDismissRequest() {
        _screenMessage.update { null }
    }


    private suspend fun _updateComments(issueNumber: String) {
        val result = DIFactory.createIssueDetailsRepository().fetchComments(issueNumber)
        if (result.isSuccess) {
            _details.update {
                it?.copy(
                    comments = result.getOrNull()?.map { it._toCommentViewData() } ?: emptyList())
            }
            _screenMessage.update {
                SnackBarMessage(
                    message = "Comments list updated ",
                    type = SnackBarMessageType.Success
                )
            }
        } else
            _updateMessageOnException(result.exceptionOrNull())

    }

    /** taking in wrapping in Result so that exception can handle by this method*/
    private fun _updateDetails(result: Result<IssueDetailsModel>) {
        try {
            val model = result.getOrThrow()
            _details.update {
                IssueDetailsViewData(
                    title = model.title,
                    issueNum = model.num,
                    creatorName = model.creator.username,
                    creatorAvatarLink = model.creator.avatarLink,
                    labels = model.labels.map{it._toLabelViewData()},
                    description = model.body,
                    status = model.status,
                    assignees = model.assigneeModel.map { it._toAssigneeViewData() }
                )
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


//TODO:Helper method section-------------  TODO:Helper method section-------------

private fun LabelModel._toLabelViewData() = LabelViewData(
    name = this.name,
    hexCode = this.hexCode,
    description = this.description
)

private fun UserShortInfoModel._toAssigneeViewData() = AssigneeViewData(
    username = this.username, avatarLink = this.avatarLink
)

private fun CommentModel._toCommentViewData() = CommentViewData(
    creatorUsername = this.user.username,
    creatorAvatarLink = this.user.avatarLink,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    body = this.body
)