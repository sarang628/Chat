package com.sarang.torang.usecase

import com.sarang.torang.data.ChatUser
import kotlinx.coroutines.flow.Flow

interface GetUserByRoomIdUseCase {
    fun invoke(roomId: Int): Flow<List<ChatUser>?>
}