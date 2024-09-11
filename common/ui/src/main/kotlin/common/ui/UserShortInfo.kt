package common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun UserShortInfo(modifier: Modifier = Modifier, username: String, avatarLink: String) {
    val labelColor = MaterialTheme.typography.labelMedium.color
    val labelStyle = MaterialTheme.typography.labelMedium.copy(
        color = labelColor.copy(alpha = 0.6f)
    )
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        AsyncImage(
            model = avatarLink,
            contentDescription = "user image",
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .clickable {

                }
        )
        Spacer(Modifier.width(4.dp))
        Text(
            modifier = Modifier,
            text = username,
            style = labelStyle
        )
    }

}