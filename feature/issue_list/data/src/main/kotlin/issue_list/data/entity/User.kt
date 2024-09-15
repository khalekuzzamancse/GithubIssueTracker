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
internal data class User(
    @SerialName("login") var login: String,
    @SerialName("id") var id: Long? = null,
    @SerialName("node_id") var nodeId: String? = null,
    @SerialName("avatar_url") var avatarUrl: String,
    @SerialName("gravatar_id") var gravatarId: String? = null,
    @SerialName("url") var url: String? = null,
    @SerialName("html_url") var htmlUrl: String? = null,
    @SerialName("followers_url") var followersUrl: String? = null,
    @SerialName("following_url") var followingUrl: String? = null,
    @SerialName("gists_url") var gistsUrl: String? = null,
    @SerialName("starred_url") var starredUrl: String? = null,
    @SerialName("subscriptions_url") var subscriptionsUrl: String? = null,
    @SerialName("organizations_url") var organizationsUrl: String? = null,
    @SerialName("repos_url") var reposUrl: String? = null,
    @SerialName("events_url") var eventsUrl: String? = null,
    @SerialName("received_events_url") var receivedEventsUrl: String? = null,
    @SerialName("type") var type: String? = null,
    @SerialName("site_admin") var siteAdmin: Boolean? = null

)