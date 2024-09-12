package issue_details.data.utils

import issue_details.data.entity.CommentEntity
import issue_details.data.entity.IssueDetailsEntity
import issue_details.domain.model.CommentModel
import issue_details.domain.model.UserShortInfoModel
import issue_details.domain.model.IssueDetailsModel
import issue_details.domain.model.LabelModel

/**
 * - Contain the helper method to convert data layer entity -> domain layer model
 * - Should not access from outer module
 */
internal class EntityToModel {
    fun toModel(entity: IssueDetailsEntity): IssueDetailsModel {
        return IssueDetailsModel(
            num = entity.number.toString(),
            title = entity.title,
            body = entity.body,
            createdTime = entity.createdAt,
            creator = UserShortInfoModel(
                username = entity.user.login,
                avatarLink = entity.user.avatarUrl
            ),
            status = entity.state,
            labels = entity.labelEntities.map { model ->
                LabelModel(
                    name = model.name,
                    hexCode = model.color,
                    description = model.description
                )
            },
            assigneeModel = entity.assignees.map { assignee ->
                UserShortInfoModel(username = assignee.login, avatarLink = assignee.avatarUrl)
            }
        )
    }

    fun toModel(entity: CommentEntity): CommentModel {
        return CommentModel(
            user = UserShortInfoModel(
                username = entity.user.login,
                avatarLink = entity.user.avatarUrl
            ),
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            body = entity.body,
            authorAssociation = entity.authorAssociation
        )
    }
}