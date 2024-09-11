package issue_details.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * - It is only for fetching API responses and follows the JSON format.
 * Other modules should not use it directly to avoid tight coupling,
 * as changes in the API response format may impact dependent modules.
 * - Should not access from outer module
 */
@Suppress("UnUsed")
@Serializable
internal data class CommentEntity(
    @SerialName("url") var url: String? = null,
    @SerialName("html_url") var htmlUrl: String? = null,
    @SerialName("issue_url") var issueUrl: String? = null,
    @SerialName("id") var id: String? = null,
    @SerialName("node_id") var nodeId: String? = null,
    @SerialName("user") var user: User,
    @SerialName("created_at") var createdAt: String,
    @SerialName("updated_at") var updatedAt: String? = null,
    @SerialName("author_association") var authorAssociation: String? = null,
    @SerialName("body") var body: String,
    @SerialName("reactions") var reactions: Reactions? = Reactions(),
    @SerialName("performed_via_github_app") var performedViaGithubApp: String? = null
)