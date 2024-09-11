package issue_list.data.entity


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
internal data class IssueEntity(
    @SerialName("url") var url: String? = null,
    @SerialName("repository_url") var repositoryUrl: String? = null,
    @SerialName("labels_url") var labelsUrl: String? = null,
    @SerialName("comments_url") var commentsUrl: String? = null,
    @SerialName("events_url") var eventsUrl: String? = null,
    @SerialName("html_url") var htmlUrl: String? = null,
    @SerialName("id") var id: Long? = null,
    @SerialName("node_id") var nodeId: String? = null,
    @SerialName("number") var number: Long,
    @SerialName("title") var title: String,
    @SerialName("user") var user: User,
    @SerialName("labels") var labelEntities: ArrayList<LabelEntity> = arrayListOf(),
    @SerialName("state") var state: String? = null,
    @SerialName("locked") var locked: Boolean? = null,
    @SerialName("assignee") var assignee: User? = null,
    @SerialName("assignees") var assignees: ArrayList<User> = arrayListOf(),
   // @SerialName("milestone") var milestone: Any? = null,
    @SerialName("comments") var comments: Int? = null,
    @SerialName("created_at") var createdAt: String,
    @SerialName("updated_at") var updatedAt: String? = null,
    @SerialName("closed_at") var closedAt: String? = null,
    @SerialName("author_association") var authorAssociation: String? = null,
    @SerialName("active_lock_reason") var activeLockReason: String? = null,
    @SerialName("body") var body: String? = null,
    @SerialName("closed_by") var closedBy: User? = null,
    @SerialName("reactions") var reactions: Reactions? = Reactions(),
    @SerialName("timeline_url") var timelineUrl: String? = null,
    @SerialName("performed_via_github_app") var performedViaGithubApp: String? = null,
   // @SerialName("state_reason") var stateReason: String? = null,
    //
    @SerialName("draft"                    ) var draft                 : Boolean?          = null,
    @SerialName("pull_request"             ) var pullRequest           : PullRequest?      = PullRequest(),


    )



