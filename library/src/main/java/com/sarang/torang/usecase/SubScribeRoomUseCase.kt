package com.sarang.torang.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface SubScribeRoomUseCase {
    suspend fun invoke(roomId: Int, coroutineScope: CoroutineScope): Flow<HashMap<String, String>>
}