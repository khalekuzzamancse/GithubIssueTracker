package com.kzcse.githubissuetracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.kzcse.githubissuetracker.ui.theme.GitHubIssueTrackerTheme
import feature.issue_details.routes.IssueDetailsRoute
import feature.issue_list.components.IssueListViewController
import feature.issue_list.route.IssuesListRoute


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubIssueTrackerTheme {
                val controller = remember { IssueListViewController() }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {

                        IssuesListRoute(
                            controller = controller,
                            onDetailsRequest = {
                                Log.d("InfoRe::", "Details")
                            },
                            onUserProfileRequest = {
                                Log.d("InfoRe::", "user")
                            }
                        )

//                        IssueDetailsRoute(
//                            modifier = Modifier
//                                .padding(16.dp)
//                                .fillMaxWidth()
//                                .verticalScroll(
//                                    rememberScrollState()
//                                ),
//                            onUserProfileRequest = {}
//                        )


                    }


                }
            }
        }
    }
}


