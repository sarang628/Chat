package com.sarang.torang.compose.chat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.data.ChatUser
import com.sarang.torang.usecase.GetUserByRoomIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ChatUiState {
    data class Success(
        val id: String = "",
        val message: String = "",
        val user: List<ChatUser> = listOf(),
        val chats: List<String> = listOf(),
    ) : ChatUiState

    object Loading : ChatUiState
    data class Error(val message: String) : ChatUiState
}

val ChatUiState.Success.isMultiple: Boolean get() = user.size > 1
val ChatUiState.Success.profileUrl: String
    get() = if (user.isEmpty()) {
        Log.e("__ChatUiState", "profileUrl is empty : chat user list is empty!")
        ""
    } else user[0].profileUrl
val ChatUiState.Success.nickName: String
    get() = if (user.isEmpty()) {
        Log.e("__ChatUiState", "nickName is empty : chat user list is empty!")
        ""
    } else if (isMultiple) user.joinToString { it.nickName } else user[0].nickName
val ChatUiState.Success.canSend: Boolean get() = message.isNotBlank()

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getUserUseCase: GetUserByRoomIdUseCase,
) : ViewModel() {

    var uiState: ChatUiState by mutableStateOf(ChatUiState.Loading)
        private set

    fun loadUser(userId: Int) {
        viewModelScope.launch {
            getUserUseCase.invoke(userId).collect {
                uiState = ChatUiState.Success(
                    user = it ?: listOf(), id = ""
                )
            }
        }
    }

    fun onMessageChange(message: String) {
        if (uiState is ChatUiState.Success) {
            (uiState as ChatUiState.Success).let {
                uiState = it.copy(message = message)
            }
        }
    }

    fun onSend() {
        if (uiState is ChatUiState.Success) {
            (uiState as ChatUiState.Success).let {
                uiState = it.copy(chats = it.chats + it.message, message = "")
            }
        }
    }

    fun loadUserByRoomId(roomId: Int) {
        viewModelScope.launch {
            getUserUseCase.invoke(roomId).collect {
                uiState = ChatUiState.Success(
                    user = it ?: listOf(), id = ""
                )
            }
        }
    }
}