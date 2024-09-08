package com.sarang.torang.compose

sealed interface ChatUiState {
    object Loading : ChatUiState
    data class Success(
        val chatItems: List<ChatRoomUiState> = listOf(),
    ) :
        ChatUiState

    data class Error(val message: String) : ChatUiState
}

data class ChatRoomUiState(
    val id: Int,
    val nickName: String,
    val seenTime: String,
    val profileUrl: String,
)

val successDummy: ChatUiState.Success
    get() = ChatUiState.Success(
        chatItems = listOf(
            ChatRoomUiState(0, "Torang", "10:00", ""),
            ChatRoomUiState(0, "sryang", "10:00", ""),
        )
    )