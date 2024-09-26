package com.sarang.torang.usecase

interface SubScribeRoomUseCase {
    suspend fun invoke(roomId: Int)
}