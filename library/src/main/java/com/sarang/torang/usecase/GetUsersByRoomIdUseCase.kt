package com.sarang.torang.usecase

import com.sarang.torang.data.ChatUser
import kotlinx.coroutines.flow.Flow

interface GetUsersByRoomIdUseCase {
    fun invoke(roomId: Int): Flow<List<ChatUser>?>
}