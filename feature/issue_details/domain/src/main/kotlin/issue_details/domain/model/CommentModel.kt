@file:Suppress("UnUsed")

package issue_details.domain.model

data class CommentModel(
    val user: UserShortInfoModel,
    val createdAt: String,
    val updatedAt: String?,
    val body: String,
)