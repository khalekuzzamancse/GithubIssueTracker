package com.kzcse.githubissuetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.kzcse.githubissuetracker.ui.theme.GitHubIssueTrackerTheme
import feature.navigation.Navigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubIssueTrackerTheme {
                Scaffold { innerPadding ->
                    Navigation(modifier = Modifier.padding(innerPadding))
                }

            }
        }
    }
}

