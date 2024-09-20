package com.sarang.torang.compose.chat

import android.util.Log
import com.sarang.torang.data.Chat
import com.sarang.torang.data.ChatUser

sealed interface ChatUiState {
    data class Success(
        val roomId: Int,
        val id: String = "",
        val message: String = "",
        val user: List<ChatUser> = listOf(),
        val chats: List<Chat> = listOf(),
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
