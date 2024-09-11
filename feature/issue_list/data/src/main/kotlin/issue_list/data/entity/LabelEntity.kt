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
internal data class LabelEntity(
    @SerialName("id") var id: Long? = null,
    @SerialName("node_id") var nodeId: String? = null,
    @SerialName("url") var url: String? = null,
    @SerialName("name") var name: String,
    @SerialName("color") var color: String,
    @SerialName("default") var default: Boolean? = null,
    @SerialName("description") var description: String="",

)
