package com.sarang.torang

sealed interface ChatUiState {
    object Loading : ChatUiState
    data class Success(
        val nickName: String,
        val chatItems: List<ChatItemUiState> = listOf(),
    ) :
        ChatUiState

    data class Error(val message: String) : ChatUiState
}

data class ChatItemUiState(
    val id: Int,
    val nickName: String,
    val seenTime: String,
    val profileUrl: String,
)

val successDummy: ChatUiState.Success
    get() = ChatUiState.Success(
        nickName = "Torang",
        chatItems = listOf(
            ChatItemUiState(0, "Torang", "10:00", ""),
            ChatItemUiState(0, "sryang", "10:00", ""),
        )
    )