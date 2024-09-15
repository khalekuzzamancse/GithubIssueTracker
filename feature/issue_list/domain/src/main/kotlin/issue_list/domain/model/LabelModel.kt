package issue_list.domain.model

/**
 * - Use it solely for data transfer to avoid tight coupling. Don't use it directly with the UI or data layer,
 * ensuring it stays independent. Changes in API response or UI format shouldn't affect it.
 */
@Suppress("Unused")
data class LabelModel(
    val name: String,
    val hexCode: String,
    val description: String,
)