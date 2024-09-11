package com.kzcse.githubissuetracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.kzcse.githubissuetracker.ui.theme.GitHubIssueTrackerTheme
import feature.issue_details.IssueDetails
import feature.issue_list.IssuesListRoute


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubIssueTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)){

//                        IssuesListRoute(
//                            onDetailsRequest = {
//                                Log.d("InfoRe::","Detials")
//                            },
//                            onCreatorInfoRequest = {
//                                Log.d("InfoRe::","user")
//                            }
//                        )
                        IssueDetails()


                    }


                }
            }
        }
    }
}


