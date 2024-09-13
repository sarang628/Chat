package com.sarang.torang.compose.chat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.compose.chatroom.ChatRoomUiState
import com.sarang.torang.data.ChatUser
import com.sarang.torang.usecase.GetUserByRoomIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatUiState(
    val id: String = "",
    val message: String = "",
    val user: List<ChatUser> = listOf(),
    val chats: List<String> = listOf(),
)

val ChatUiState.isMultiple: Boolean get() = user.size > 1
val ChatUiState.profileUrl: String
    get() = if (user.isEmpty()) {
        Log.e("__ChatUiState", "profileUrl is empty : chat user list is empty!")
        ""
    } else user[0].profileUrl
val ChatUiState.nickName: String
    get() =
        if (user.isEmpty()) {
            Log.e("__ChatUiState", "nickName is empty : chat user list is empty!")
            ""
        } else if (isMultiple) user.joinToString { it.nickName } else user[0].nickName
val ChatUiState.canSend: Boolean get() = message.isNotBlank()

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getUserUseCase: GetUserByRoomIdUseCase,
) : ViewModel() {

    fun loadUser(roomId: Int) {
        viewModelScope.launch {
            getUserUseCase.invoke(roomId).collect {
                uiState =
                    uiState.copy(user = it, id = "")
            }
        }
    }

    fun onMessageChange(it: String) {
        uiState = uiState.copy(message = it)
    }

    fun onSend() {
        uiState = uiState.copy(chats = uiState.chats + uiState.message, message = "")
    }

    var uiState by mutableStateOf(ChatUiState())
        private set
}