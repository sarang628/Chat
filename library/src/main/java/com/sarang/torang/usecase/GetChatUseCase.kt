package com.sarang.torang.usecase

import com.sarang.torang.compose.ChatUiState
import kotlinx.coroutines.flow.Flow

interface GetChatUseCase {
    fun invoke(roomId: Int): Flow<List<ChatUiState>>
}