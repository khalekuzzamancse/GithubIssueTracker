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
internal data class Reactions(
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
