package com.kzcse.githubissuetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import common.ui.SnackBar
import feature_navigation.route.Navigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val viewmodel = viewModel { NetworkViewModel(context) }


            Theme {
                Scaffold(
                    snackbarHost = {
                        viewmodel.screenMessage.collectAsState().value?.let {
                            SnackBar(
                                message = it,
                                onDismissRequest =viewmodel::onScreenMessageDismissRequest
                            )
                        }

                    }
                ) { innerPadding ->
                    Navigation(
                        modifier = Modifier.padding(innerPadding),
                        onScreenMessageUpdate = viewmodel::onScreenMessageUpdate

                    )
                }

            }
        }
    }
}

