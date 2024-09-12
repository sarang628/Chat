package com.sarang.torang.compose.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class ChatUiState(
    val nickName: String = "",
    val id: String = "",
)

@HiltViewModel
class ChatViewModel @Inject constructor(

) : ViewModel() {
    var uiState by mutableStateOf(ChatUiState())
        private set
}