package issue_list.domain.model

/**
 * - Use it solely for data transfer to avoid tight coupling. Don't use it directly with the UI or data layer,
 * ensuring it stays independent. Changes in API response or UI format shouldn't affect it.
 * @param creatorName is github user name of the issue creator
 */
@Suppress("Unused")
data class IssueModel(
    val id: String,
    val title: String,
    val createdTime: String,
    val userAvatarLink: String,
    val creatorName: String,
    val labels: List<LabelModel> = emptyList(),
)
