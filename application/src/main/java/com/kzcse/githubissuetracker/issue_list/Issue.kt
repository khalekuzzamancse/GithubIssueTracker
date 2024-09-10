//package com.kzcse.githubissuetracker.issue_list
//
//data class Issue (
//
//  @SerializedName("url"                      ) var url                   : String?           = null,
//  @SerializedName("repository_url"           ) var repositoryUrl         : String?           = null,
//  @SerializedName("labels_url"               ) var labelsUrl             : String?           = null,
//  @SerializedName("comments_url"             ) var commentsUrl           : String?           = null,
//  @SerializedName("events_url"               ) var eventsUrl             : String?           = null,
//  @SerializedName("html_url"                 ) var htmlUrl               : String?           = null,
//  @SerializedName("id"                       ) var id                    : Int?              = null,
//  @SerializedName("node_id"                  ) var nodeId                : String?           = null,
//  @SerializedName("number"                   ) var number                : Int?              = null,
//  @SerializedName("title"                    ) var title                 : String?           = null,
//  @SerializedName("user"                     ) var user                  : User?             = User(),
//  @SerializedName("labels"                   ) var labels                : ArrayList<Labels> = arrayListOf(),
//  @SerializedName("state"                    ) var state                 : String?           = null,
//  @SerializedName("locked"                   ) var locked                : Boolean?          = null,
//  @SerializedName("assignee"                 ) var assignee              : String?           = null,
//  @SerializedName("assignees"                ) var assignees             : ArrayList<String> = arrayListOf(),
//  @SerializedName("milestone"                ) var milestone             : String?           = null,
//  @SerializedName("comments"                 ) var comments              : Int?              = null,
//  @SerializedName("created_at"               ) var createdAt             : String?           = null,
//  @SerializedName("updated_at"               ) var updatedAt             : String?           = null,
//  @SerializedName("closed_at"                ) var closedAt              : String?           = null,
//  @SerializedName("author_association"       ) var authorAssociation     : String?           = null,
//  @SerializedName("active_lock_reason"       ) var activeLockReason      : String?           = null,
//  @SerializedName("draft"                    ) var draft                 : Boolean?          = null,
//  @SerializedName("pull_request"             ) var pullRequest           : PullRequest?      = PullRequest(),
//  @SerializedName("body"                     ) var body                  : String?           = null,
//  @SerializedName("closed_by"                ) var closedBy              : String?           = null,
//  @SerializedName("reactions"                ) var reactions             : Reactions?        = Reactions(),
//  @SerializedName("timeline_url"             ) var timelineUrl           : String?           = null,
//  @SerializedName("performed_via_github_app" ) var performedViaGithubApp : String?           = null,
//  @SerializedName("state_reason"             ) var stateReason           : String?           = null
//
//)