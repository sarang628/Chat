package com.sarang.torang.usecase

interface LoadChatUseCase {
    suspend fun invoke(roomId: Int)
}