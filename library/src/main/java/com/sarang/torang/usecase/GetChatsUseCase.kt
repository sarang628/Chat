package com.sarang.torang.usecase

import com.sarang.torang.data.Chat
import kotlinx.coroutines.flow.Flow

interface GetChatsUseCase {
    fun invoke(roomId: Int): Flow<List<Chat>>
}