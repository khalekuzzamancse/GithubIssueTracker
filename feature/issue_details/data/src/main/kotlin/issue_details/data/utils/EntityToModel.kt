package issue_details.data.utils

import issue_details.data.entity.IssueDetailsEntity
import issue_details.domain.model.IssueDetailsModel
import issue_details.domain.model.LabelModel

/**
 * - Contain the helper method to convert data layer entity -> domain layer model
 * - Should not access from outer module
 */
internal class EntityToModel {
    fun toModel(entity: IssueDetailsEntity): IssueDetailsModel {
        return IssueDetailsModel(
            num = entity.number,
            title = entity.title,
            body = entity.body,
            createdTime = entity.createdAt,
            creatorAvatarLink = entity.user.avatarUrl,
            creatorUsername = entity.user.login,
            labels = entity.labelEntities.map { model ->
                LabelModel(
                    name = model.name,
                    hexCode = model.color,
                    description = model.description
                )
            }
        )
    }
}