package com.sarang.torang.usecase

import com.sarang.torang.compose.ChatRoomUiState
import kotlinx.coroutines.flow.Flow

interface LoadChatRoomUseCase {
    suspend fun invoke()
}