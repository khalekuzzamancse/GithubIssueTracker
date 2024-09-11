@file:Suppress("FunctionName","ComposableNaming")

package common.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LabelListView(modifier: Modifier = Modifier,labels:List<LabelViewData>) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        labels.forEach { label ->
            LabelView(//From common:ui module
                name = label.name,
                hexCode = label.hexCode,
                description = label.description
            )
        }
    }

}

data class LabelViewData(
    val name: String,
    val hexCode: String,
    val description: String?,
)
@SuppressLint("ComposableNaming")
@Composable
fun LabelView(
    modifier: Modifier = Modifier,
    name: String,
    hexCode:String,
    description: String?=null,
) {
    var showDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(_hexToColor(hexCode))
            .padding(2.dp)
            .clickable {
                showDialog = true
            }
    ) {
        Text(text = name)
    }
    if (showDialog) {
        _DescriptionDialog(
            description = description ?: "No description found",
            onDismissRequest = {
                showDialog = false
            })
    }


}

data class Label(
    val name: String,
    val hexCode: String,
    val description: String?,
)

@Composable
private fun _DescriptionDialog(
    description: String,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = { Text(text = description) },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("OK")
            }
        }
    )

}

//TODO:Helper function section
private fun _hexToColor(hexColor: String): Color {
    // Ensure the hex string has a '#' and is 7 characters long (including '#')
    val colorString = if (hexColor.startsWith("#")) hexColor else "#$hexColor"
    // Parse the color string to a long and create a Color object
    return Color(android.graphics.Color.parseColor(colorString))
}