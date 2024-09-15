@file:Suppress("FunctionName", "ComposableNaming")

package common.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay

enum class SnackBarMessageType { Error, Success }

data class SnackBarMessage(
    val message: String,
    val type: SnackBarMessageType = SnackBarMessageType.Error,
    val details: String? = null
)

@Composable
fun SnackBar(
    message: SnackBarMessage,
    durationInMs: Long = 4000L,
    onDismissRequest: () -> Unit,
) {

    /**
     * if dialog is showing but the snackBar is hide because if updated as null
     * In that case dialogue should not hide automatically because user reading the details
     * So the details need to preserve
     */
    val details = message.details
    var showDetails by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(durationInMs)
        val detailsInNotShowing = !showDetails
        if (detailsInNotShowing)
            onDismissRequest()
    }
    _SnackBar(
        message = message.message,
        type = message.type,
        showAction = details != null,
        onDetailsRequest = {
            showDetails = true
        })

    details?.let {
        if (showDetails) {
            _DetailsDialog(
                details = it,
                onDismissRequest = {
                    showDetails = false
                    onDismissRequest()
                }
            )
        }

    }

}


@Composable
private fun _SnackBar(
    modifier: Modifier = Modifier,
    message: String,
    type: SnackBarMessageType,
    showAction: Boolean,
    onDetailsRequest: () -> Unit,
) {
    val containerColor = if (type == SnackBarMessageType.Error) Color.Red else Color(
        android.graphics.Color.parseColor("#4BB543")
    )
    val textColor = if (containerColor.luminance() > 0.5) Color.Black else Color.White
    Snackbar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = textColor,
        action = {
            if (showAction) {
                TextButton(onClick = {
                    onDetailsRequest()
                }) {
                    Text(stringResource(R.string.details), color = textColor)
                }
            }
        }
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun _DetailsDialog(
    modifier: Modifier = Modifier,
    details: String,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.details)) },
        text = {
            Text(
                text = details,
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.ok))
            }
        }
    )
}
