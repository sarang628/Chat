package com.sarang.torang.usecase

import com.sarang.torang.compose.chatroom.ChatRoomUiState
import kotlinx.coroutines.flow.Flow

interface GetChatRoomUseCase {
    fun invoke() : Flow<List<ChatRoomUiState>>
}