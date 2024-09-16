package issue_details.data.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * - It is only for fetching API responses and follows the JSON format.
 * Other modules should not use it directly to avoid tight coupling,
 * as changes in the API response format may impact dependent modules.
 * - Should not access from outer module
 */
@Suppress("Unused")
@Serializable
internal data class IssueDetailsEntity(
    @SerialName("comments_url") var commentsUrl: String? = null,
    @SerialName("number") var number: Long,
    @SerialName("title") var title: String,
    @SerialName("user") var user: User,
    @SerialName("labels") var labelEntities: ArrayList<LabelEntity> = arrayListOf(),
    @SerialName("state") var state: String = "Unknown",
    @SerialName("assignee") var assignee: User? = null,
    @SerialName("assignees") var assignees: ArrayList<User> = arrayListOf(),
    @SerialName("created_at") var createdAt: String,
    @SerialName("body") var body: String = "",
)



