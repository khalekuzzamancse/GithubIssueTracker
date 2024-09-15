package issue_details.domain.model

/**
 * - Use it solely for data transfer to avoid tight coupling. Don't use it directly with the UI or data layer,
 * ensuring it stays independent. Changes in API response or UI format shouldn't affect it.
 */
@Suppress("Unused")
data class IssueDetailsModel(
    val num: String,
    val title: String,
    val body: String,
    val createdTime: String,
    val creator: UserShortInfoModel,
    val labels: List<LabelModel>,
    val assigneeModel: List<UserShortInfoModel>,
    val status: String,
)

