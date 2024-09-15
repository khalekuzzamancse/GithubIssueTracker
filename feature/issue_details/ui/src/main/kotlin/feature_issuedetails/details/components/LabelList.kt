package feature_issuedetails.details.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import common.ui.LabelListView
import common.ui.LabelViewData
import common.ui.TextWithLessOpacity
import feature_issuedetails.R

/**Represent the list of labels for a particular issue*/
@Composable
internal fun LabelList(modifier: Modifier = Modifier, labels: List<LabelViewData>) {
   if(labels.isEmpty())
       TextWithLessOpacity(modifier = modifier, text = stringResource(R.string.no_label_found))
    LabelListView(//defined in common:ui module
        modifier = modifier, labels = labels
    )
}