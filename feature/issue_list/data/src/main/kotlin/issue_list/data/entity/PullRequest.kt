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
internal data class PullRequest (
    @SerialName("url"       ) var url      : String? = null,
    @SerialName("html_url"  ) var htmlUrl  : String? = null,
    @SerialName("diff_url"  ) var diffUrl  : String? = null,
    @SerialName("patch_url" ) var patchUrl : String? = null,
    @SerialName("merged_at" ) var mergedAt : String? = null

)