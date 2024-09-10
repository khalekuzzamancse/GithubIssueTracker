import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PullRequest (
  @SerialName("url"       ) var url      : String? = null,
  @SerialName("html_url"  ) var htmlUrl  : String? = null,
  @SerialName("diff_url"  ) var diffUrl  : String? = null,
  @SerialName("patch_url" ) var patchUrl : String? = null,
  @SerialName("merged_at" ) var mergedAt : String? = null

)