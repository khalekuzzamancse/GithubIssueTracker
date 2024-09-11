package issue_list.data.utils

import issue_list.data.entity.IssueEntity
import issue_list.domain.model.IssueModel
import issue_list.domain.model.LabelModel

/**
 * - Contain the helper method to convert data layer entity -> domain layer model
 * - Should not access from outer module
 */
internal class EntityToModel {
    fun toEntity(entity: IssueEntity): IssueModel {
        return IssueModel(
            id = entity.number.toString(),
            title = entity.title,
            createdTime = entity.createdAt,
            userAvatarLink = entity.user.avatarUrl,
            creatorName = entity.user.login,
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