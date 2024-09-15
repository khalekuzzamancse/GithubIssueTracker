package common.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun TextWithLessOpacity(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.labelSmall,
    opacity: Float = 0.6f
) {
    Text(
        modifier = modifier,
        text = text,
        style = style.copy(color = style.color.copy(alpha = opacity))
    )

}