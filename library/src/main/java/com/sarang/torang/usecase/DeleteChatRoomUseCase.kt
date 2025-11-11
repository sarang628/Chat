package com.sarang.torang.usecase

interface DeleteChatRoomUseCase {
    suspend fun invoke(roomId : Int)
}