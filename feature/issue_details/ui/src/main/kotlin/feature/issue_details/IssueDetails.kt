package feature.issue_details

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jeziellago.compose.markdowntext.MarkdownText
import issue_details.di_container.DIFactory

@Composable
fun IssueDetails(modifier: Modifier = Modifier) {
    var body by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        val response = DIFactory.createIssueDetailsRepository().fetchDetails("154717")
        if (response.isSuccess) {
            body = response.getOrNull()?.body
            Log.d("APIResponse", response.getOrThrow().body.toString())
        }
    }
    //body is not loaded yet..
    if (body==null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(64.dp))
        }
    }
    else{
        body?.let {
            MarkdownText(
                markdown = it.replace("\\r\\n", "\n"),
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        }
    }

}

//Minimal example
@Preview
@Composable
fun MinimalExampleContent() {
    val markdownContent = """
    ### Issue Link\r\n\r\n#154166\r\n\r\n### Target\r\n\r\nstable\r\n\r\n### Cherry pick PR Link\r\n\r\n#152203\r\n\r\n### Changelog Description\r\n\r\nAfter Rolling Flutter engine according to this pr, Chinese font weight on HyperOS and ColorOS devices backs to normal.\r\n\r\n### Impacted Users\r\n\r\nAndroid devices with ColorOS or HyperOS.\r\n\r\n### Impact Description\r\n\r\nOn ColorOS, Chinese font weight w300\\~w500 become thinner;\r\nOn HyperOS, Chinese font weight w400 and above become thicker.\r\n\r\n### Workaround\r\n\r\nDowngrade Flutter SDK version to 3.22.3.\r\n\r\n### Risk\r\n\r\nlow\r\n\r\n### Test Coverage\r\nyes\r\n\r\n### Validation Steps\r\n\r\n_No response_
""".trimIndent()
    MarkdownText(
        markdown = markdownContent.replace("\\r\\n", "\n"),
    )
}


