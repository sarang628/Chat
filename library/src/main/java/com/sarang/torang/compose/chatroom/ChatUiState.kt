package com.sarang.torang.compose.chatroom

import android.util.Log
import com.sarang.torang.data.ChatUser
import java.text.SimpleDateFormat
import java.util.Locale

sealed interface ChatUiState {
    object Loading : ChatUiState
    data class Success(
        val chatItems: List<ChatRoomUiState> = listOf(),
    ) :
        ChatUiState

    data class Error(val message: String) : ChatUiState
    object Logout : ChatUiState
}

data class ChatRoomUiState(
    val id: Int,
    val seenTime: String,
    val list: List<ChatUser> = listOf(),
)

val timeFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA)

val ChatRoomUiState.seenTimeTxt : String get() =
    try {
        "${((System.currentTimeMillis() - timeFormat.parse(this.seenTime).time) / (1000 * 60 * 60 * 24))}일 전"
    }catch (e : Exception){
        ""
    }

val ChatRoomUiState.isMultiple: Boolean get() = list.size > 1
val ChatRoomUiState.profileUrl: String
    get() = if (list.isEmpty()) {
        Log.e("__ChatUiState", "profileUrl is empty : chat user list is empty!")
        ""
    } else list[0].profileUrl
val ChatRoomUiState.nickName: String
    get() =
        if (list.isEmpty()) {
            Log.e("__ChatUiState", "nickName is empty : chat user list is empty!")
            ""
        } else if (isMultiple) list.joinToString { it.nickName } else list[0].nickName

val successDummy: ChatUiState.Success
    get() = ChatUiState.Success(
        chatItems = listOf(
            ChatRoomUiState(
                0,
                "Torang",
                listOf(ChatUser(nickName = "nickName", profileUrl = "1", id = "id"))
            ),
            ChatRoomUiState(
                0,
                "Torang",
                listOf(ChatUser(nickName = "nickName", profileUrl = "1", id = "id"))
            ),
        )
    )