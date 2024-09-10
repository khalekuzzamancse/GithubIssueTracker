package com.kzcse.githubissuetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kzcse.githubissuetracker.ui.theme.GitHubIssueTrackerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubIssueTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)){
                        IssuesListRoute()
//                        IssueDetails()
//                        LaunchedEffect(Unit) {
//                            APIFacade().requestIssueList()
//                        }

                    }


                }
            }
        }
    }
}



@Preview
@Composable
private fun Test() {
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
        Text(text = "Hellsafajsfasjfafjkajfkajfkajfakjwiffafaejkarjkaaaaaaaaaaaaaaajkkkkkkkjhhhhhho"
        , modifier = Modifier.weight(1f))
        Text(text = "12-12-24", modifier = Modifier)
    }

}