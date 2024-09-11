package feature.issue_details.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.ui.LabelListView
import common.ui.LabelViewData

/**Represent the list of labels for a particular issue*/
@Composable
internal fun LabelList(modifier: Modifier = Modifier, labels: List<LabelViewData>) {
    LabelListView(//defined in common:ui module
        modifier = modifier, labels = labels
    )
}