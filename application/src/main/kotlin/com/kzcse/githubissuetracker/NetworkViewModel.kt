package com.kzcse.githubissuetracker

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.ui.SnackBarMessage
import common.ui.SnackBarMessageType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NetworkViewModel(context: Context) : ViewModel() {

    private val networkMonitor = NetworkMonitor(context)
    private val _message = MutableStateFlow<SnackBarMessage?>(null)
    val screenMessage = _message.asStateFlow()

    init {
        viewModelScope.launch {
            networkMonitor.isConnected.collect { connected ->
                if (!connected) {
                    onScreenMessageUpdate(
                        SnackBarMessage(
                            message = context.getString(R.string.internet_connection_has_gone),
                            type = SnackBarMessageType.Error,
                            details = context.getString(R.string.you_have_no_internet_connection_some_functionality_may_not_work_properly)
                        )
                    )
                } else {
                    _message.update { null }
                }

            }
        }
    }

     fun onScreenMessageUpdate(message: SnackBarMessage?) {
            _message.update { message }

    }
    fun onScreenMessageDismissRequest(){
        _message.update { null }
    }
}
