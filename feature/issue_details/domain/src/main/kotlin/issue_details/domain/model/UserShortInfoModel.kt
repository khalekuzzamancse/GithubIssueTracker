package issue_details.domain.model
/**
 * - Use it solely for data transfer to avoid tight coupling. Don't use it directly with the UI or data layer,
 * ensuring it stays independent. Changes in API response or UI format shouldn't affect it.
 */
@Suppress("Unused")
data class UserShortInfoModel(
    val username:String,
    val avatarLink:String
)