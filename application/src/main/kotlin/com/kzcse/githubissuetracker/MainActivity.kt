package com.kzcse.githubissuetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kzcse.githubissuetracker.ui.theme.GitHubIssueTrackerTheme
import feature.issue_details.routes.IssueDetailsRoute


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubIssueTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {

//                        IssuesListRoute(
//                            onDetailsRequest = {
//                                Log.d("InfoRe::","Details")
//                            },
//                            onCreatorInfoRequest = {
//                                Log.d("InfoRe::","user")
//                            }
//                        )
                        //   IssueDetails()
                        IssueDetailsRoute(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                        )


                    }


                }
            }
        }
    }
}


