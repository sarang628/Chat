package com.sarang.torang.usecase

interface SendChatUseCase {
    suspend fun invoke(roomId: Int, message: String)
}