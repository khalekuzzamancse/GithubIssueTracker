package com.kzcse.githubissuetracker.issue_details

import PullRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueDetailsEntity(
    @SerialName("url") var url: String? = null,
    @SerialName("repository_url") var repositoryUrl: String? = null,
    @SerialName("labels_url") var labelsUrl: String? = null,
    @SerialName("comments_url") var commentsUrl: String? = null,
    @SerialName("events_url") var eventsUrl: String? = null,
    @SerialName("html_url") var htmlUrl: String? = null,
    @SerialName("id") var id: Long? = null,
    @SerialName("node_id") var nodeId: String? = null,
    @SerialName("number") var number: Int? = null,
    @SerialName("title") var title: String? = null,
    @SerialName("user") var user: User? = User(),
    @SerialName("labels") var labelEntities: ArrayList<LabelEntity> = arrayListOf(),
    @SerialName("state") var state: String? = null,
    @SerialName("locked") var locked: Boolean? = null,
    @SerialName("assignee") var assignee: User? = null,
    @SerialName("assignees") var assignees: ArrayList<User> = arrayListOf(),
    @SerialName("milestone") var milestone: String? = null,
    @SerialName("comments") var comments: Int? = null,
    @SerialName("created_at") var createdAt: String? = null,
    @SerialName("updated_at") var updatedAt: String? = null,
    @SerialName("closed_at") var closedAt: String? = null,
    @SerialName("author_association") var authorAssociation: String? = null,
    @SerialName("active_lock_reason") var activeLockReason: String? = null,
    @SerialName("body") var body: String? = null,
    @SerialName("closed_by") var closedBy: User? = null,
    @SerialName("reactions") var reactions: Reactions? = Reactions(),
    @SerialName("timeline_url") var timelineUrl: String? = null,
    @SerialName("performed_via_github_app") var performedViaGithubApp: String? = null,
    @SerialName("state_reason") var stateReason: String? = null,
    //
    @SerialName("draft"                    ) var draft                 : Boolean?          = null,
    @SerialName("pull_request"             ) var pullRequest           : PullRequest?      = PullRequest(),


    )

@Serializable
data class LabelEntity(

    @SerialName("id") var id: Long? = null,
    @SerialName("node_id") var nodeId: String? = null,
    @SerialName("url") var url: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("color") var color: String? = null,
    @SerialName("default") var default: Boolean? = null,
    @SerialName("description") var description: String? = null

)

@Serializable
data class Reactions(
    @SerialName("url") var url: String? = null,
    @SerialName("total_count") var totalCount: Int? = null,
    @SerialName("+1") var plus: Int? = null,
    @SerialName("-1") var minus: Int? = null,
    @SerialName("laugh") var laugh: Int? = null,
    @SerialName("hooray") var hooray: Int? = null,
    @SerialName("confused") var confused: Int? = null,
    @SerialName("heart") var heart: Int? = null,
    @SerialName("rocket") var rocket: Int? = null,
    @SerialName("eyes") var eyes: Int? = null

)

@Serializable
data class User(
    @SerialName("login") var login: String? = null,
    @SerialName("id") var id: Long? = null,
    @SerialName("node_id") var nodeId: String? = null,
    @SerialName("avatar_url") var avatarUrl: String? = null,
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